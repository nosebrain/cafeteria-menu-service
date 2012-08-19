package de.nosebrain.widget.cafeteria.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author nosebrain
 */
public class UniversityInfo {
	private String name;
	private List<CafeteriaInfo> cafeteriaInfos;

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the cafeteriaInfos
	 */
	public List<CafeteriaInfo> getCafeteriaInfos() {
		if (this.cafeteriaInfos == null) {
			this.cafeteriaInfos = new LinkedList<CafeteriaInfo>();
		}
		return this.cafeteriaInfos;
	}

	/**
	 * @param cafeteriaInfos the cafeteriaInfos to set
	 */
	public void setCafeteriaInfos(final List<CafeteriaInfo> cafeteriaInfos) {
		this.cafeteriaInfos = cafeteriaInfos;
	}


}
