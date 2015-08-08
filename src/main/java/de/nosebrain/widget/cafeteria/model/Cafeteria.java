package de.nosebrain.widget.cafeteria.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Cafeteria {

  @JsonInclude(Include.NON_DEFAULT)
  private boolean closed;
  private List<Day> days;
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd.MM.yyyy", timezone="Europe/Berlin")
  private Date weekStart;
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd.MM.yyyy", timezone="Europe/Berlin")
  private Date weekEnd;
  @JsonIgnore
  private Date lastUpdated;
  
  private String foodInfo = "";

  /**
   * @return the closed
   */
  public boolean isClosed() {
    return this.closed;
  }

  /**
   * @param closed
   *          the closed to set
   */
  public void setClosed(final boolean closed) {
    this.closed = closed;
  }

  /**
   * @return the days
   */
  public List<Day> getDays() {
    if (this.days == null) {
      this.days = new LinkedList<Day>();
    }
    return this.days;
  }

  /**
   * @param days
   *          the days to set
   */
  public void setDays(final List<Day> days) {
    this.days = days;
  }

  public void addDay(final Day day) {
    if (this.days == null) {
      this.days = new LinkedList<Day>();
    }

    this.days.add(day);
  }

  /**
   * @return the weekStart
   */
  public Date getWeekStart() {
    return this.weekStart;
  }

  /**
   * @param weekStart
   *          the weekStart to set
   */
  public void setWeekStart(final Date weekStart) {
    this.weekStart = weekStart;
  }

  /**
   * @return the weekEnd
   */
  public Date getWeekEnd() {
    return this.weekEnd;
  }

  /**
   * @param weekEnd
   *          the weekEnd to set
   */
  public void setWeekEnd(final Date weekEnd) {
    this.weekEnd = weekEnd;
  }

  /**
   * @return the lastUpdated
   */
  public Date getLastUpdated() {
    return this.lastUpdated;
  }

  /**
   * @param lastUpdated the lastUpdated to set
   */
  public void setLastUpdated(final Date lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  /**
   * @return the foodInfo
   */
  public String getFoodInfo() {
    return this.foodInfo;
  }

  /**
   * @param foodInfo
   *          the foodInfo to set
   */
  public void setFoodInfo(final String foodInfo) {
    this.foodInfo = foodInfo;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + (this.closed ? 1231 : 1237);
    result = (prime * result) + ((this.days == null) ? 0 : this.days.hashCode());
    result = (prime * result) + ((this.foodInfo == null) ? 0 : this.foodInfo.hashCode());
    result = (prime * result) + ((this.weekEnd == null) ? 0 : this.weekEnd.hashCode());
    result = (prime * result) + ((this.weekStart == null) ? 0 : this.weekStart.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final Cafeteria other = (Cafeteria) obj;
    if (this.closed != other.closed) {
      return false;
    }
    if (this.days == null) {
      if (other.days != null) {
        return false;
      }
    } else if (!this.days.equals(other.days)) {
      return false;
    }
    if (this.foodInfo == null) {
      if (other.foodInfo != null) {
        return false;
      }
    } else if (!this.foodInfo.equals(other.foodInfo)) {
      return false;
    }
    if (this.weekEnd == null) {
      if (other.weekEnd != null) {
        return false;
      }
    } else if (!this.weekEnd.equals(other.weekEnd)) {
      return false;
    }
    if (this.weekStart == null) {
      if (other.weekStart != null) {
        return false;
      }
    } else if (!this.weekStart.equals(other.weekStart)) {
      return false;
    }
    return true;
  }
}
