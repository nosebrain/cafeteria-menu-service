package de.nosebrain.widget.cafeteria;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeSet;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import de.nosebrain.widget.cafeteria.model.config.CachePolicy;
import de.nosebrain.widget.cafeteria.model.config.CafeteriaInfo;
import de.nosebrain.widget.cafeteria.model.config.UniversityInfo;
import de.nosebrain.widget.cafeteria.parser.MenuParser;

public class CafeteriaConfigurer implements BeanFactoryPostProcessor {

  private static CafeteriaInfo getCafeteriaInfo(final List<CafeteriaInfo> infos, final int index) {
    try {
      return infos.get(index);
    } catch (final IndexOutOfBoundsException exception) {
      final CafeteriaInfo cafeteriaInfo = new CafeteriaInfo();
      for (int i = infos.size() - 1; i < index; i++) {
        infos.add(null);
      }
      infos.set(index, cafeteriaInfo);
      return cafeteriaInfo;
    }
  }

  private Properties properties;
  private Map<String, UniversityInfo> configMap;

  @Override
  public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
    for (final Object keyO : new TreeSet<Object>(this.properties.keySet())) {
      final String key = (String) keyO;
      if (key.startsWith("cafeteria.")) {
        final String[] parts = key.split("\\.");
        final String uniKey = parts[1];
        UniversityInfo universityInfo = this.configMap.get(uniKey);
        if (universityInfo == null) {
          universityInfo = new UniversityInfo();
          universityInfo.setId(uniKey);
          this.configMap.put(uniKey, universityInfo);
        }
        final String value = this.properties.getProperty(key);
        if (parts.length == 3) {
          // TODO: color for widget
          universityInfo.setName(value);
        } else if (parts.length == 4) {
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
            cafeteriaInfo.setCachePolicy(CachePolicy.valueOf(value.toUpperCase()));
          }
          
          if ("url".equals(last)) {
            cafeteriaInfo.setUrl(value);
          }
        }
      }
    }

    // fill the parsers with the parsed information
    for (final Entry<String, UniversityInfo> entry : this.configMap.entrySet()) {
      final String key = entry.getKey();
      final MenuParser parser = beanFactory.getBean(key + "_parser", MenuParser.class);
      final UniversityInfo value = entry.getValue();
      value.setParser(parser);
      beanFactory.registerSingleton(key, value);
    }
  }

  /**
   * @param properties
   *          the properties to set
   */
  public void setProperties(final Properties properties) {
    this.properties = properties;
  }

  /**
   * @param configMap
   *          the configMap to set
   */
  public void setConfigMap(final Map<String, UniversityInfo> configMap) {
    this.configMap = configMap;
  }
}
