package de.nosebrain.widget.cafeteria.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.model.UniversityInfo;

public abstract class AbstractMenuParser implements MenuParser {

	private UniversityInfo uniInfo;

	public final Cafeteria updateCafeteria(final int id, final int week) throws Exception {
		final String url = this.getUrlForId(id);
		final Document document = Jsoup.connect(url).get();
		return this.extractInformations(document, week);
	}

	protected abstract Cafeteria extractInformations(Document document, int week);

	private String getUrlForId(final int id) {
		try {
			return this.uniInfo.getCafeteriaInfos().get(id).getUrl();
		} catch (final IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("unknown cafeteria, id=" + id);
		}
	}

	public void setUniversityInfo(final UniversityInfo universityInfo) {
		this.uniInfo = universityInfo;
	}
}
