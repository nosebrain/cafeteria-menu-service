package de.nosebrain.widget.cafeteria.model.config;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * representation a cafeteria
 * 
 * @author nosebrain
 */
public class CafeteriaInfo {
  private int id;
  private String name;
  private String url;
  private Location location;
  private CachePolicy cachePolicy = CachePolicy.WEEK;
  private boolean disabled;
  @JsonIgnore
  private UniversityInfo universityInfo;
  
  /**
   * @return the id
   */
  public int getId() {
    return this.id;
  }

  /**
   * @param id the id to set
   */
  public void setId(final int id) {
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
   * @return the location
   */
  public Location getLocation() {
    return location;
  }

  /**
   * @param location the location to set
   */
  public void setLocation(Location location) {
    this.location = location;
  }

  /**
   * @return the cachePolicy
   */
  public CachePolicy getCachePolicy() {
    return this.cachePolicy;
  }

  /**
   * @param cachePolicy the cachePolicy to set
   */
  public void setCachePolicy(final CachePolicy cachePolicy) {
    this.cachePolicy = cachePolicy;
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

  /**
   * @return the universityInfo
   */
  public UniversityInfo getUniversityInfo() {
    return this.universityInfo;
  }

  /**
   * @param universityInfo the universityInfo to set
   */
  public void setUniversityInfo(final UniversityInfo universityInfo) {
    this.universityInfo = universityInfo;
  }
}
