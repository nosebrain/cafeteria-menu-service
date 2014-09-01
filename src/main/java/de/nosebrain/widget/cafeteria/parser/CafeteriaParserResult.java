package de.nosebrain.widget.cafeteria.parser;

import de.nosebrain.widget.cafeteria.model.Cafeteria;

public class CafeteriaParserResult {
  private Cafeteria cafeteria;
  
  private String metaData;
  
  public CafeteriaParserResult() {
    super();
  }
  
  public CafeteriaParserResult(final Cafeteria cafeteria) {
    super();
    this.cafeteria = cafeteria;
  }
  
  public CafeteriaParserResult(final Cafeteria cafeteria, final String metaData) {
    super();
    this.cafeteria = cafeteria;
    this.metaData = metaData;
  }
  
  /**
   * @return the cafeteria
   */
  public Cafeteria getCafeteria() {
    return this.cafeteria;
  }
  
  /**
   * @param cafeteria the cafeteria to set
   */
  public void setCafeteria(final Cafeteria cafeteria) {
    this.cafeteria = cafeteria;
  }

  /**
   * @return the metaData
   */
  public String getMetaData() {
    return this.metaData;
  }

  /**
   * @param metaData the metaData to set
   */
  public void setMetaData(final String metaData) {
    this.metaData = metaData;
  }
}
