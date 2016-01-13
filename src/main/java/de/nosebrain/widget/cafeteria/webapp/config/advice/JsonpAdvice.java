package de.nosebrain.widget.cafeteria.webapp.config.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
  public JsonpAdvice() {
    super("callback");
  }
}
