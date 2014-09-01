package de.nosebrain.widget.cafeteria.controller;

import static de.nosebrain.util.ValidationUtils.present;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.nosebrain.widget.cafeteria.CafeteriaService;
import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.model.UniversityInfo;

@Controller
public class CafeteriaController {

  private static final SimpleGrantedAuthority ADMIN_ROLE = new SimpleGrantedAuthority("admin");
  
  private static final String UNI_PLACEHOLDER = "uni";
  private static final String CAFETERIA_PLACEHOLDER = "id";
  private static final String WEEK_PLACEHOLDER = "week";
  
  private static final String CAFETERIA_MAPPING = "/{" + UNI_PLACEHOLDER + "}/{" + CAFETERIA_PLACEHOLDER + "}/{" + WEEK_PLACEHOLDER + "}";

  @Autowired
  private CafeteriaService service;

  @Autowired
  private Map<String, UniversityInfo> universityInfo;

  @RequestMapping("/")
  @ResponseBody
  public Map<String, UniversityInfo> getSupportedSites() {
    return this.universityInfo;
  }

  @RequestMapping(CAFETERIA_MAPPING)
  @ResponseBody
  public Cafeteria getCafeteria(@PathVariable(UNI_PLACEHOLDER) final String uni, @PathVariable(CAFETERIA_PLACEHOLDER) final int cafeteria, @PathVariable(WEEK_PLACEHOLDER) final int week, @RequestParam(value = "force", required = false) boolean force, final Authentication principal) {
    if (force) {
      if (!isAdmin(principal)) {
        force = false;
      }
    }
    return this.service.getCafeteria(uni, cafeteria, week, force);
  }

  private static boolean isAdmin(final Authentication principal) {
    if (!present(principal)) {
      return false;
    }
    return principal.getAuthorities().contains(ADMIN_ROLE);
  }

  @ExceptionHandler(Throwable.class)
  @ResponseBody
  public Status handleException(final Throwable e, final HttpServletResponse response) {
    response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
    return new Status(e.getMessage());
  }
}
