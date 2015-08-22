package de.nosebrain.widget.cafeteria.webapp.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "de.nosebrain.widget.cafeteria" })
@org.springframework.context.annotation.PropertySource(value = { "classpath:cafeteria-service.properties", "file:${catalina.home}/conf/cafeteria-service/cafeteria-service.properties" }, ignoreResourceNotFound = true)
public class CafeteriaServiceConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/admin/assets/**").addResourceLocations("/WEB-INF/assets/");
  }
  
  @Bean(name = { "serviceProperties" })
  public static Properties getServiceProperties(final Environment environment) {
    final Properties properties = new Properties();
    for (final PropertySource<?> propertySource : ((ConfigurableEnvironment) environment).getPropertySources()) {
      if (propertySource instanceof EnumerablePropertySource<?>) {
          for (final String key : ((EnumerablePropertySource<?>) propertySource).getPropertyNames()) {
            final Object value = propertySource.getProperty(key);
            if ((value != null) && (value instanceof String)) {
              properties.setProperty(key, (String) value);
            }
          }
      }
    }
    return properties;
  }
  
  @Bean
  public ServletContextTemplateResolver templateResolver(@Value("${dev.thymeleaf.cacheable}") final boolean cacheable) {
      final ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
      resolver.setPrefix("/WEB-INF/templates/");
      resolver.setSuffix(".html");
      resolver.setTemplateMode("HTML5");
      resolver.setOrder(1);
      resolver.setCacheable(cacheable);
      return resolver;
  }
  
  @Bean
  public SpringTemplateEngine templateEngine(final ServletContextTemplateResolver resolver) {
      final SpringTemplateEngine engine = new SpringTemplateEngine();
      engine.setTemplateResolver(resolver);
      return engine;
  }
  
  @Bean
  public ThymeleafViewResolver thymeleafViewResolver(final SpringTemplateEngine engine) {
      final ThymeleafViewResolver resolver = new ThymeleafViewResolver();
      resolver.setTemplateEngine(engine);
      return resolver;
  }
  
  @Bean
  public static CafeteriaConfigurer configurer(@Qualifier("serviceProperties") final Properties properties) {
    final CafeteriaConfigurer configurer = new CafeteriaConfigurer();
    configurer.setProperties(properties);
    return configurer;
  }
  
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
     return new PropertySourcesPlaceholderConfigurer();
  }
}
