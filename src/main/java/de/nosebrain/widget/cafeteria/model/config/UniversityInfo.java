package de.nosebrain.widget.cafeteria.model.config;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.nosebrain.widget.cafeteria.parser.MenuParser;

/**
 * 
 * @author nosebrain
 */
public class UniversityInfo {
  private String id;
  private String name;
  private List<CafeteriaInfo> cafeterias;
  @JsonIgnore
  private MenuParser parser;
  
  /**
   * @return the id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id the id to set
   */
  public void setId(final String id) {
    this.id = id;
  }

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

  /**
   * @return the parser
   */
  public MenuParser getParser() {
    return this.parser;
  }

  /**
   * @param parser the parser to set
   */
  public void setParser(final MenuParser parser) {
    this.parser = parser;
  }

}
