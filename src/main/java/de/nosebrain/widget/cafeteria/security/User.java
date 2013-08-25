package de.nosebrain.widget.cafeteria.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @author nosebrain
 */
public class User implements UserDetails {
  private static final long serialVersionUID = 588006611800252489L;
  
  
  private final Collection<? extends GrantedAuthority> authorities;
  private final String password;
  private final String username;

  public User(final String username, final String password,
      final Collection<? extends GrantedAuthority> authorities) {
    super();
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    // TODO Auto-generated method stub
    return true;
  }

}
