package de.nosebrain.widget.cafeteria.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.nosebrain.widget.cafeteria.CafeteriaService;
import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.model.UniversityInfo;

@Controller
public class CafeteriaController {

	@Autowired
	private CafeteriaService service;

	@Autowired
	private Map<String, UniversityInfo> universityInfo;

	@RequestMapping("/")
	public @ResponseBody Map<String, UniversityInfo> getSupportedSites() {
		return this.universityInfo;
	}

	@RequestMapping("/{uni}/{id}/{week}")
	public @ResponseBody Cafeteria getCafeteria(@PathVariable("uni") final String uni, @PathVariable("id") final int cafeteria, @PathVariable("week") final int week) {
		return this.service.getCafeteria(uni, cafeteria, week);
	}
}
