package de.nosebrain.widget.cafeteria.webapp.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import de.nosebrain.spring.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class CafeteriaServiceSecurityConfig extends WebSecurityConfigurerAdapter {
  public static final String ADMIN_ROLE = "admin";
  
  @Autowired
  public void registerGlobalAuthentication(final AuthenticationManagerBuilder auth, final AuthenticationProvider customProiver) throws Exception {
    auth.authenticationProvider(customProiver);
  }
  
  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/admin/**").hasAnyAuthority(ADMIN_ROLE).anyRequest().permitAll().and()
    .httpBasic().realmName("Cafeteria Service");
  }
  
  @Bean
  protected AuthenticationProvider inMemoryUserDetailsManager(@Qualifier(CafeteriaServiceConfig.SERVICE_PROPERTIES) final Properties properties) {
    final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    final InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager(properties, "auth.");
    authenticationProvider.setUserDetailsService(userDetailsService);
    return authenticationProvider;
  }

}
