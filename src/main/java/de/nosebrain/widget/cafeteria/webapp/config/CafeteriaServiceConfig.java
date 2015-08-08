package de.nosebrain.widget.cafeteria.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "de.nosebrain.widget.cafeteria" })
@PropertySource(value = { "classpath:cafeteria-service.properties", "file:${catalina.home}/conf/cafeteria-service/cafeteria-service.properties" }, ignoreResourceNotFound = true)
public class CafeteriaServiceConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
  }
  
  @Bean
  public static CafeteriaConfigurer configurer(final Environment env) {
    final CafeteriaConfigurer configurer = new CafeteriaConfigurer();
    configurer.setEnvironment(env);
    return configurer;
  }
  
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
     return new PropertySourcesPlaceholderConfigurer();
  }
}
