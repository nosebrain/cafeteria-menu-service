package de.nosebrain.widget.cafeteria.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.model.Day;
import de.nosebrain.widget.cafeteria.model.config.CafeteriaInfo;

public abstract class AbstractMenuParser implements MenuParser {
  private static final int TIMEOUT = 500 * 1000; // in ms

  private static final String EURO = "€";

  protected static String cleanPrice(final String string) {
    if (string.contains(EURO)) {
      return string.replace(EURO, "").trim();
    }

    return string;
  }

  @Override
  public CafeteriaParserResult updateCafeteria(final CafeteriaInfo cafeteriaInfo, final int week) throws Exception {
    if (cafeteriaInfo.isDisabled()) {
      final Cafeteria closedCafeteria = new Cafeteria();
      for (int i = 0; i < 5; i++) {
        final Day day = new Day();
        day.setHoliday(true);
        closedCafeteria.addDay(day);
      }
      return new CafeteriaParserResult(closedCafeteria);
    }

    final String url = cafeteriaInfo.getUrl();
    final Document document = Jsoup.connect(url).timeout(TIMEOUT).get();
    return new CafeteriaParserResult(this.extractInformations(document, week), document.html());
  }

  protected abstract Cafeteria extractInformations(Document document, int week) throws Exception;
}