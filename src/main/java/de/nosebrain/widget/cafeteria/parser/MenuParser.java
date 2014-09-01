package de.nosebrain.widget.cafeteria.parser;

import de.nosebrain.widget.cafeteria.model.UniversityInfo;

public interface MenuParser {

  public CafeteriaParserResult updateCafeteria(final int id, final int week) throws Exception;

  public void setUniversityInfo(UniversityInfo universityInfo);
}
