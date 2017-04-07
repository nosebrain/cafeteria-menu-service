package de.nosebrain.widget.cafeteria.service;

import static de.nosebrain.util.ValidationUtils.present;

import java.util.Date;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.nosebrain.common.exception.ResourceNotFoundException;
import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.model.config.CafeteriaInfo;
import de.nosebrain.widget.cafeteria.parser.CafeteriaParserResult;
import de.nosebrain.widget.cafeteria.parser.MenuParser;

/**
 * 
 * @author nosebrain
 */
@Component
public class CafeteriaService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CafeteriaService.class);

  @Autowired
  private CafeteriaStore client;

  public Cafeteria getCafeteria(final CafeteriaInfo cafeteriaInfo, final int year, final int week, final boolean forceUpdate) throws ResourceNotFoundException {
    final String key = key(cafeteriaInfo, week, year);
    final Cafeteria storedCafeteria = this.client.getCafeteria(key);
    final boolean useCache = canUseCache(cafeteriaInfo, storedCafeteria);
    if (!forceUpdate && useCache) {
      return storedCafeteria;
    }
    
    final MenuParser parser = cafeteriaInfo.getUniversityInfo().getParser();
    try {
      final CafeteriaParserResult cafeteriaResult = parser.updateCafeteria(cafeteriaInfo, week);
      final Cafeteria cafeteria = cafeteriaResult.getCafeteria();
      if (cafeteria != null) {
        // save in database only if changed
        if (!cafeteria.equals(storedCafeteria)) {
          this.client.storeCafeteria(key, cafeteria);
          
          final String metaData = cafeteriaResult.getMetaData();
          if (present(metaData)) {
            this.client.storeMetaData(key, metaData);
          }
        }
        return cafeteria;
      }
    } catch (final Exception e) {
      LOGGER.error("Updating or caching cafeteria failed.", e);
    }

    throw new ResourceNotFoundException();
  }

  private static boolean canUseCache(final CafeteriaInfo cafeteriaInfo, final Cafeteria storedCafeteria) {
    if ((storedCafeteria == null)) {
      return false;
    }
    if (storedCafeteria.isClosed()) {
      return true;
    }
    switch (cafeteriaInfo.getCachePolicy()) {
      case WEEK:
        return true;
      case DAY:
        final Date nowDate = new Date();
        final DateMidnight now = new DateTime(nowDate).toDateMidnight();
        final DateMidnight lastUpdated = new DateTime(storedCafeteria.getLastUpdated()).toDateMidnight();
        return now.isEqual(lastUpdated);
      default:
        break;
    }
    return false;
  }

  private static String key(final CafeteriaInfo cafeteriaInfo, final int week, final int year) {
    return cafeteriaInfo.getUniversityInfo().getId() + "_" + cafeteriaInfo.getId() + "_" + week + "_" + year;
  }
}
