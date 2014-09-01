package de.nosebrain.widget.cafeteria;

import static de.nosebrain.util.ValidationUtils.present;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.nosebrain.common.exception.ResourceNotFoundException;
import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.parser.CafeteriaParserResult;
import de.nosebrain.widget.cafeteria.parser.MenuParser;
import de.nosebrain.widget.cafeteria.service.CafeteriaStore;

/**
 * 
 * @author nosebrain
 */
@Component
public class CafeteriaService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CafeteriaService.class);

  @Autowired
  private Map<String, MenuParser> parserMap;

  @Autowired
  private CafeteriaStore client;

  public Cafeteria getCafeteria(final String uni, final int id, final int year, final int week, final boolean forceUpdate) throws ResourceNotFoundException {
    final String key = key(uni, id, week, year);
    final Cafeteria storedCafeteria = this.client.getCafeteria(key);
    if (!forceUpdate && (storedCafeteria != null)) {
      return storedCafeteria;
    }

    final MenuParser parser = this.parserMap.get(uni + "_parser");
    if (parser == null) {
      throw new IllegalArgumentException(uni + " not supported");
    }

    try {
      final CafeteriaParserResult cafeteriaResult = parser.updateCafeteria(id, week);
      final Cafeteria cafeteria = cafeteriaResult.getCafeteria();
      if (cafeteria != null) {
        // save in database
        this.client.storeCafeteria(key, cafeteria);
        
        final String metaData = cafeteriaResult.getMetaData();
        if (present(metaData)) {
          this.client.storeMetaData(key, metaData);
        }
        return cafeteria;
      }
    } catch (final Exception e) {
      LOGGER.error("exeception while updating or caching cafeteria.", e);
    }

    throw new ResourceNotFoundException();
  }

  private static String key(final String uni, final int id, final int week, final int year) {
    return uni + "_" + id + "_" + week + "_" + year;
  }
}
