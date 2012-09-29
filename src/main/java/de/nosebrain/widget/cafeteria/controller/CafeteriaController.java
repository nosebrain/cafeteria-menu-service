package de.nosebrain.widget.cafeteria.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.nosebrain.widget.cafeteria.CafeteriaService;
import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.model.UniversityInfo;

@Controller
public class CafeteriaController {

  private static final String CAFETERIA_MAPPING = "/{uni}/{id}/{week}";

  @Autowired
  private CafeteriaService service;

  @Autowired
  private Map<String, UniversityInfo> universityInfo;

  @RequestMapping("/")
  public @ResponseBody
  Map<String, UniversityInfo> getSupportedSites() {
    return this.universityInfo;
  }

  @RequestMapping(CAFETERIA_MAPPING)
  public @ResponseBody Cafeteria getCafeteria(@PathVariable("uni") final String uni, @PathVariable("id") final int cafeteria, @PathVariable("week") final int week) {
    return this.service.getCafeteria(uni, cafeteria, week, false);
  }
  
  @RequestMapping(CAFETERIA_MAPPING + "/force")
  public @ResponseBody Cafeteria getCafeteriaForce(@PathVariable("uni") final String uni, @PathVariable("id") final int cafeteria, @PathVariable("week") final int week) {
    // TODO: secure service
    return this.service.getCafeteria(uni, cafeteria, week, true);
  }

  @ExceptionHandler(Throwable.class)
  public @ResponseBody Status handleException(final Throwable e, final HttpServletResponse response) {
    response.setStatus(HttpStatus.SC_SERVICE_UNAVAILABLE);
    return new Status(e.getMessage());
  }
}
