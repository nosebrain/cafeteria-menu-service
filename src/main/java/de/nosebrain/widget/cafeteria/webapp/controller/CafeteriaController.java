package de.nosebrain.widget.cafeteria.webapp.controller;

import static de.nosebrain.util.ValidationUtils.present;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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

import de.nosebrain.common.exception.ResourceNotFoundException;
import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.model.config.CafeteriaInfo;
import de.nosebrain.widget.cafeteria.model.config.UniversityInfo;
import de.nosebrain.widget.cafeteria.service.CafeteriaService;
import de.nosebrain.widget.cafeteria.webapp.config.CafeteriaServiceSecurityConfig;
import de.nosebrain.widget.cafeteria.webapp.model.Status;

@Controller
public class CafeteriaController {

  private static final SimpleGrantedAuthority ADMIN_ROLE = new SimpleGrantedAuthority(CafeteriaServiceSecurityConfig.ADMIN_ROLE);
  
  private static final String UNI_PLACEHOLDER = "uni";
  private static final String CAFETERIA_PLACEHOLDER = "id";
  private static final String WEEK_PLACEHOLDER = "week";
  
  private static final String CAFETERIA_MAPPING = "/{" + UNI_PLACEHOLDER + "}/{" + CAFETERIA_PLACEHOLDER + ":\\d+}/{" + WEEK_PLACEHOLDER + "}";

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
  public Cafeteria getCafeteria(@PathVariable(UNI_PLACEHOLDER) final String uni, @PathVariable(CAFETERIA_PLACEHOLDER) final int cafeteriaId, @PathVariable(WEEK_PLACEHOLDER) final String yearAndWeek, @RequestParam(value = "force", required = false) boolean force, final Authentication principal) throws ResourceNotFoundException {
    // check for permission to force complete reload
    if (force) {
      if (!isAdmin(principal)) {
        force = false;
      }
    }
    final int week;
    int year;
    
    if (!present(yearAndWeek)) {
      throw new IllegalArgumentException("invalid year and week");
    }
    
    final Calendar calendar = Calendar.getInstance(Locale.GERMANY); // XXX: change locale depending on cafeteria
    switch (yearAndWeek) {
      case "CURRENT":
      case "CURRENT_WEEK":
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        year = calendar.get(Calendar.YEAR);
        week = calendar.get(Calendar.WEEK_OF_YEAR);
        break;
      case "NEXT_WEEK":
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        year = calendar.get(Calendar.YEAR);
        week = calendar.get(Calendar.WEEK_OF_YEAR) + 1;
        break;
      default:
        if (yearAndWeek.contains("_")) {
          final String[] yearWeekSplit = yearAndWeek.split("_");
          year = Integer.parseInt(yearWeekSplit[0]);
          week = Integer.parseInt(yearWeekSplit[1]);
        } else {
          week = Integer.parseInt(yearAndWeek);
          year = calendar.get(Calendar.YEAR);
          final int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
          if (currentWeek > week) {
            year++;
          }
        }
        break;
    }
    
    if ((week < 1) || (week > 54)) {
      throw new IllegalArgumentException("invalid week '" + week + "'");
    }
    
    if ((year / 1000) < 1) {
      throw new IllegalArgumentException("invalid year '" + year + "'");
    }
    
    if (!this.universityInfo.containsKey(uni)) {
      throw new IllegalArgumentException(uni + " not supported");
    }
    
    final UniversityInfo universityInfo = this.universityInfo.get(uni);
    final List<CafeteriaInfo> cafeteriaInfos = universityInfo.getCafeterias();
    
    if ((cafeteriaId < 0) || (cafeteriaId >= cafeteriaInfos.size())) {
      throw new IllegalArgumentException("unknown cafeteria, id=" + cafeteriaId);
    }
    
    final CafeteriaInfo cafeteriaInfo = cafeteriaInfos.get(cafeteriaId);
    
    final Cafeteria cafeteria = this.service.getCafeteria(cafeteriaInfo, year, week, force);
    
    return cafeteria;
  }

  private static boolean isAdmin(final Authentication principal) {
    if (!present(principal)) {
      return false;
    }
    return principal.getAuthorities().contains(ADMIN_ROLE);
  }
  
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  public Status handleResourceNotFoundException(final IllegalArgumentException e, final HttpServletResponse response) {
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    return new Status(e.getMessage());
  }
  
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseBody
  public Status handleResourceNotFoundException(final ResourceNotFoundException e, final HttpServletResponse response) {
    response.setStatus(HttpStatus.NOT_FOUND.value());
    return new Status("no menu found");
  }

  @ExceptionHandler(Throwable.class)
  @ResponseBody
  public Status handleException(final Throwable e, final HttpServletResponse response) {
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    return new Status(e.getMessage());
  }
}
