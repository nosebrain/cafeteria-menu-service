package de.nosebrain.widget.cafeteria.parser;

import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.model.UniversityInfo;

public interface MenuParser {

  public Cafeteria updateCafeteria(final int id, final int week) throws Exception;

  public void setUniversityInfo(UniversityInfo universityInfo);
}
