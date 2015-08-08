package de.nosebrain.widget.cafeteria.parser;

import de.nosebrain.widget.cafeteria.model.config.CafeteriaInfo;

public interface MenuParser {

  public CafeteriaParserResult updateCafeteria(CafeteriaInfo cafeteriaInfo, final int week) throws Exception;
}
