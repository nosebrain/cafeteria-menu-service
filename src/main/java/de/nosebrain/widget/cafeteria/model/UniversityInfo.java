package de.nosebrain.widget.cafeteria.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author nosebrain
 */
public class UniversityInfo {
  private String name;
  private List<CafeteriaInfo> cafeterias;

  /**
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * @return the cafeteriaInfos
   */
  public List<CafeteriaInfo> getCafeterias() {
    if (this.cafeterias == null) {
      this.cafeterias = new LinkedList<CafeteriaInfo>();
    }
    return this.cafeterias;
  }

  /**
   * @param cafeteriaInfos
   *          the cafeteriaInfos to set
   */
  public void setCafeterias(final List<CafeteriaInfo> cafeterias) {
    this.cafeterias = cafeterias;
  }

}
