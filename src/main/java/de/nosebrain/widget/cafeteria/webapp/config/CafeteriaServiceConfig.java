package de.nosebrain.widget.cafeteria.webapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "de.nosebrain.widget.cafeteria" })
@PropertySource(value = { "classpath:cafeteria-service.properties", "file:${catalina.home}/conf/cafeteria-service/cafeteria-service.properties" }, ignoreResourceNotFound = true)
public class CafeteriaServiceConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/admin/assets/**").addResourceLocations("/WEB-INF/assets/");
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
