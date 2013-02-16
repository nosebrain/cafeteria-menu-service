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
}
