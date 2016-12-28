package de.nosebrain.widget.cafeteria.webapp.config;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;

import de.nosebrain.widget.cafeteria.model.config.Location;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import de.nosebrain.widget.cafeteria.model.config.CachePolicy;
import de.nosebrain.widget.cafeteria.model.config.CafeteriaInfo;
import de.nosebrain.widget.cafeteria.model.config.UniversityInfo;
import de.nosebrain.widget.cafeteria.parser.MenuParser;

public class CafeteriaConfigurer implements BeanFactoryPostProcessor {

  public static final String CAFETERIA_MAP_KEY = "cafeteriaInfoMap";

  private static CafeteriaInfo getCafeteriaInfo(final List<CafeteriaInfo> infos, final int index) {
    if (infos.size() <= index) {
      for (int i = infos.size(); i <= index; i++) {
        infos.add(null);
      }
    }

    CafeteriaInfo cafeteria = infos.get(index);
    if (cafeteria == null) {
      final CafeteriaInfo cafeteriaInfo = new CafeteriaInfo();
      infos.set(index, cafeteriaInfo);
      cafeteria = cafeteriaInfo;
    }

    return cafeteria;
  }

  private Properties properties;

  @Override
  public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
    final Map<String, UniversityInfo> configMap = new TreeMap<>();
    for (final Object key0 : this.properties.keySet()) {
      if (key0 instanceof String) {
        final String key = (String) key0;

        if (key.startsWith("cafeteria.")) {
          final String[] parts = key.split("\\.");
          final String uniKey = parts[1];
          UniversityInfo universityInfo = configMap.get(uniKey);
          if (universityInfo == null) {
            universityInfo = new UniversityInfo();
            universityInfo.setId(uniKey);
            configMap.put(uniKey, universityInfo);
          }
          final String value = beanFactory.resolveEmbeddedValue(PropertyPlaceholderConfigurer.DEFAULT_PLACEHOLDER_PREFIX + key + PropertyPlaceholderConfigurer.DEFAULT_PLACEHOLDER_SUFFIX);
          if (parts.length == 3) {
            if ("name".equals(parts[2])) {
              universityInfo.setName(value);
            }
          } else if (parts.length >= 4) {
            final int index = Integer.parseInt(parts[2]);

            final List<CafeteriaInfo> cafeterias = universityInfo.getCafeterias();
            final CafeteriaInfo cafeteriaInfo = getCafeteriaInfo(cafeterias, index);
            cafeteriaInfo.setUniversityInfo(universityInfo);
            cafeteriaInfo.setId(index);
            final String last = parts[3];
            if ("name".equals(last)) {
              cafeteriaInfo.setName(value);
            }

            if ("disabled".equals(last)) {
              cafeteriaInfo.setDisabled(Boolean.parseBoolean(value));
            }

            if ("cachePolicy".equals(last)) {
              cafeteriaInfo
                  .setCachePolicy(CachePolicy.valueOf(value.toUpperCase()));
            }

            if ("url".equals(last)) {
              cafeteriaInfo.setUrl(value);
            }

            if ("location".equals(last)) {
              Location currentLocation = cafeteriaInfo.getLocation();
              if (currentLocation == null) {
                currentLocation = new Location();
                cafeteriaInfo.setLocation(currentLocation);
              }

              final double coordinate = Double.parseDouble(value);
			  final String coordinateKey = parts[4];
              if ("latitude".equals(coordinateKey)) {
				currentLocation.setLatitude(coordinate);
			  } else {
              	currentLocation.setLongitude(coordinate);
			  }
			}
          }
        }
      }
    }

    // set the parsers and register the university
    for (final Entry<String, UniversityInfo> entry : configMap.entrySet()) {
      final String key = entry.getKey();
      final MenuParser parser = beanFactory.getBean(key + "_parser",
          MenuParser.class);
      final UniversityInfo value = entry.getValue();
      value.setParser(parser);
      beanFactory.registerSingleton(key, value);
    }
  }

  /**
   * @param properties the properties to set
   */
  public void setProperties(final Properties properties) {
    this.properties = properties;
  }
}
