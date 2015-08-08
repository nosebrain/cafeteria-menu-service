package de.nosebrain.widget.cafeteria.webapp.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import de.nosebrain.widget.cafeteria.model.config.CachePolicy;
import de.nosebrain.widget.cafeteria.model.config.CafeteriaInfo;
import de.nosebrain.widget.cafeteria.model.config.UniversityInfo;
import de.nosebrain.widget.cafeteria.parser.MenuParser;

public class CafeteriaConfigurer implements BeanFactoryPostProcessor {

  public static final String CAFETERIA_MAP_KEY = "cafeteriaInfoMap";

  private static CafeteriaInfo getCafeteriaInfo(final List<CafeteriaInfo> infos, final int index) {
    if (infos.size() < index) {
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
  
  private Environment environment;

  @Override
  public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
    final Map<String, UniversityInfo> configMap = new HashMap<String, UniversityInfo>();
    
    if (this.environment instanceof ConfigurableEnvironment) {
      for (final PropertySource<?> propertySource : ((ConfigurableEnvironment) this.environment).getPropertySources()) {
          if (propertySource instanceof EnumerablePropertySource<?>) {
              for (final String key : ((EnumerablePropertySource<?>) propertySource).getPropertyNames()) {
                if (key.startsWith("cafeteria.")) {
                  final String[] parts = key.split("\\.");
                  final String uniKey = parts[1];
                  UniversityInfo universityInfo = configMap.get(uniKey);
                  if (universityInfo == null) {
                    universityInfo = new UniversityInfo();
                    universityInfo.setId(uniKey);
                    configMap.put(uniKey, universityInfo);
                  }
                  final String value = this.environment.getProperty(key);
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
          }
      }
    }

    // set the parsers and register the university
    for (final Entry<String, UniversityInfo> entry : configMap.entrySet()) {
      final String key = entry.getKey();
      final MenuParser parser = beanFactory.getBean(key + "_parser", MenuParser.class);
      final UniversityInfo value = entry.getValue();
      value.setParser(parser);
      beanFactory.registerSingleton(key, value);
    }
  }

  /**
   * @param environment the environment to set
   */
  public void setEnvironment(final Environment environment) {
    this.environment = environment;
  }
}
