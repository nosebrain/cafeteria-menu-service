package de.nosebrain.widget.cafeteria.model;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Day {

  private List<Menu> food;
  @JsonInclude(Include.NON_DEFAULT)
  private boolean holiday;
  private String message;

  /**
   * @return the food
   */
  public List<Menu> getFood() {
    return this.food;
  }

  /**
   * @param food
   *          the food to set
   */
  public void setFood(final List<Menu> food) {
    this.food = food;
  }

  /**
   * @return the holiday
   */
  public boolean isHoliday() {
    return this.holiday;
  }

  /**
   * @param holiday
   *          the holiday to set
   */
  public void setHoliday(final boolean holiday) {
    this.holiday = holiday;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * @param message
   *          the message to set
   */
  public void setMessage(final String message) {
    this.message = message;
  }

  public void addMenu(final Menu menu) {
    if (this.food == null) {
      this.food = new LinkedList<Menu>();
    }

    this.food.add(menu);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + ((this.food == null) ? 0 : this.food.hashCode());
    result = (prime * result) + (this.holiday ? 1231 : 1237);
    result = (prime * result) + ((this.message == null) ? 0 : this.message.hashCode());
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
    final Day other = (Day) obj;
    if (this.food == null) {
      if (other.food != null) {
        return false;
      }
    } else if (!this.food.equals(other.food)) {
      return false;
    }
    if (this.holiday != other.holiday) {
      return false;
    }
    if (this.message == null) {
      if (other.message != null) {
        return false;
      }
    } else if (!this.message.equals(other.message)) {
      return false;
    }
    return true;
  }
}
