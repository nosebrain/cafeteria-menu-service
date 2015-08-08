package de.nosebrain.widget.cafeteria.webapp;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class CafeteriaServiceSecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
  
  public CafeteriaServiceSecurityWebApplicationInitializer() {
    // do not initialize anything
  }
  
  @Override
  protected String getDispatcherWebApplicationContextSuffix() {
    return CafeteriaServiceApplicationInitializer.SERVLET_NAME;
  }

}
