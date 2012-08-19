package de.nosebrain.widget.cafeteria.model;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class Menu implements Serializable {
	private static final long serialVersionUID = -3042285582845795034L;


	private String description;
	private List<String> prices;

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the prices
	 */
	public List<String> getPrices() {
		return this.prices;
	}

	/**
	 * @param prices the prices to set
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
}
