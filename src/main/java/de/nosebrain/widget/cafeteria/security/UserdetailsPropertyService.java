package de.nosebrain.widget.cafeteria.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Deprecated // TODO: user inmemory user details manager
public class UserdetailsPropertyService implements UserDetailsService, InitializingBean {

  private static final String ROLE_PREFIX = "ROLE_";
  public static final String APPLICATION_ROLE = "ROLE_USER";
  public static final String ADMIN_ROLE = "ROLE_ADMIN";


  private static final String PREFIX = "auth.";
  
  
  private Properties properties;
  private Map<String, UserDetails> users;
  
  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    if (this.users.containsKey(username)) {
      return this.users.get(username);
    }
    throw new UsernameNotFoundException(username);
  }
  
  @Override
  public void afterPropertiesSet() throws Exception {
    this.users = new HashMap<String, UserDetails>();
    
    for (final Entry<Object, Object> property : this.properties.entrySet()) {
      final String propertyName = property.getKey().toString();
      if (propertyName.startsWith(PREFIX)) {
        final String username = propertyName.replaceFirst(PREFIX, "");
        final String passwordRole = this.properties.getProperty(propertyName);
        final String[] split = passwordRole.split(";;");
        final String password = split[0];
        final String role = ROLE_PREFIX + split[1].toUpperCase();
        
        final Collection<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role));
        if (ADMIN_ROLE.equals(role)) {
          authorities.add(new SimpleGrantedAuthority(APPLICATION_ROLE));
        }
        
        final User user = new User(username, password, authorities);
        this.users.put(username, user);
      }
    }
  }

  /**
   * @param properties the properties to set
   */
  public void setProperties(final Properties properties) {
    this.properties = properties;
  }
}
