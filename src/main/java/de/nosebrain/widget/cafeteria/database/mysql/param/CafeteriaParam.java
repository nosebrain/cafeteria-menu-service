package de.nosebrain.widget.cafeteria.database.mysql.param;

import java.util.Date;

public class CafeteriaParam {
  private String key;
  private String value;
  
  private Date lastUpdate;
  
  private int limit;
  
  /**
   * @return the key
   */
  public String getKey() {
    return this.key;
  }
  
  /**
   * @param key the key to set
   */
  public void setKey(final String key) {
    this.key = key;
  }
  
  /**
   * @return the value
   */
  public String getValue() {
    return this.value;
  }
  
  /**
   * @param value the value to set
   */
  public void setValue(final String value) {
    this.value = value;
  }

  /**
   * @return the lastUpdate
   */
  public Date getLastUpdate() {
    return this.lastUpdate;
  }

  /**
   * @param lastUpdate the lastUpdate to set
   */
  public void setLastUpdate(final Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  /**
   * @return the limit
   */
  public int getLimit() {
    return this.limit;
  }

  /**
   * @param limit the limit to set
   */
  public void setLimit(final int limit) {
    this.limit = limit;
  }
}
