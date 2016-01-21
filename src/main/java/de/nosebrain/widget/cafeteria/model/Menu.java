package de.nosebrain.widget.cafeteria.model;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Menu {

  private String description;
  private List<String> ingredients;
  private List<String> prices;

  /**
   * @return the description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(final String description) {
    this.description = description;
  }

  /**
   * @return the ingredients
   */
  public List<String> getIngredients() {
    return this.ingredients;
  }

  /**
   * @param ingredients the ingredients to set
   */
  public void setIngredients(final List<String> ingredients) {
    this.ingredients = ingredients;
  }

  /**
   * @return the prices
   */
  public List<String> getPrices() {
    return this.prices;
  }

  /**
   * @param prices
   *          the prices to set
   */
  public void setPrices(final List<String> prices) {
    this.prices = prices;
  }

  public void addPrice(final String price) {
    if (this.prices == null) {
      this.prices = new LinkedList<String>();
    }

    this.prices.add(price);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result)
        + ((this.description == null) ? 0 : this.description.hashCode());
    result = (prime * result) + ((this.prices == null) ? 0 : this.prices.hashCode());
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
    final Menu other = (Menu) obj;
    if (this.description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!this.description.equals(other.description)) {
      return false;
    }
    if (this.prices == null) {
      if (other.prices != null) {
        return false;
      }
    } else if (!this.prices.equals(other.prices)) {
      return false;
    }
    return true;
  }
}
