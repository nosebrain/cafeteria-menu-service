package de.nosebrain.widget.cafeteria.controller;

public class Status {
	
	private String message;
	
	/**
	 * constructor
	 * @param message the status message
	 */
	public Status(final String message) {
		this.setMessage(message);
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(final String message) {
		this.message = message;
	}
}
