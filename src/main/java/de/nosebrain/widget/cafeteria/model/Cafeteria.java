package de.nosebrain.widget.cafeteria.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Cafeteria implements Serializable {
	private static final long serialVersionUID = -6266505147840167563L;


	private boolean closed;
	private List<Day> days;
	private Date weekStart;
	private Date weekEnd;
	private String foodInfo = "";

	/**
	 * @return the closed
	 */
	public boolean isClosed() {
		return this.closed;
	}

	/**
	 * @param closed the closed to set
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
	 * @param days the days to set
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
	 * @param weekStart the weekStart to set
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
	 * @param weekEnd the weekEnd to set
	 */
	public void setWeekEnd(final Date weekEnd) {
		this.weekEnd = weekEnd;
	}

	/**
	 * @return the foodInfo
	 */
	public String getFoodInfo() {
		return this.foodInfo;
	}

	/**
	 * @param foodInfo the foodInfo to set
	 */
	public void setFoodInfo(final String foodInfo) {
		this.foodInfo = foodInfo;
	}
}
