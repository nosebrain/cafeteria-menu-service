package de.nosebrain.widget.cafeteria.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.nosebrain.widget.cafeteria.service.CafeteriaStore;

@Controller
public class AdminController {
  
  @Autowired
  private CafeteriaStore cafeteriaStore;
  
  @RequestMapping("/admin/cache")
  public String getAdminCacheOverview(final Model model, @RequestParam(defaultValue = "10") final int limit) {
    model.addAttribute("cafeterias", this.cafeteriaStore.getCafeterias(limit));
    model.addAttribute("limit", limit);
    return "admin/cache";
  }
}
