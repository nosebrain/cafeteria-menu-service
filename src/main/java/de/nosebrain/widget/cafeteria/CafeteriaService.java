package de.nosebrain.widget.cafeteria;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.nosebrain.widget.cafeteria.model.Cafeteria;
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

  public Cafeteria getCafeteria(final String uni, final int id, final int week, final boolean forceUpdate) {
    final String key = key(uni, id, week);
    final Cafeteria storedCafeteria = this.client.getCafeteria(key);
    if (!forceUpdate && (storedCafeteria != null)) {
      return storedCafeteria;
    }

    final MenuParser parser = this.parserMap.get(uni + "_parser");
    if (parser == null) {
      throw new IllegalArgumentException(uni + " not supported");
    }

    try {
      final Cafeteria cafeteria = parser.updateCafeteria(id, week);
      if (cafeteria != null) {
        // save in database
        this.client.storeCafeteria(key, cafeteria);
        return cafeteria;
      }
    } catch (final Exception e) {
      LOGGER.error("exeception while updating or caching cafeteria.", e);
    }

    throw new IllegalStateException("can't get any menu for cafeteria");
  }

  private static String key(final String uni, final int id, final int week) {
    return uni + "_" + id + "_" + week;
  }
}
