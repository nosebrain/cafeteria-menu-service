package de.nosebrain.widget.cafeteria.model;

/**
 * representation a cafeteria
 * 
 * @author nosebrain
 */
public class CafeteriaInfo {
  private String name;
  private String url;
  private boolean disabled;

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
   * @return the url
   */
  public String getUrl() {
    return this.url;
  }

  /**
   * @param url
   *          the url to set
   */
  public void setUrl(final String url) {
    this.url = url;
  }

  /**
   * @return the disabled
   */
  public boolean isDisabled() {
    return this.disabled;
  }

  /**
   * @param disabled
   *          the disabled to set
   */
  public void setDisabled(final boolean disabled) {
    this.disabled = disabled;
  }
}
