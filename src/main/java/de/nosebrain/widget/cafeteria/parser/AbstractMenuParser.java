package de.nosebrain.widget.cafeteria.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.model.CafeteriaInfo;
import de.nosebrain.widget.cafeteria.model.Day;
import de.nosebrain.widget.cafeteria.model.UniversityInfo;

public abstract class AbstractMenuParser implements MenuParser {
  private static final String EURO = "€";

  protected static String cleanPrice(final String string) {
    if (string.contains(EURO)) {
      return string.replace(EURO, "").trim();
    }

    return string;
  }

  private UniversityInfo uniInfo;

  @Override
  public final CafeteriaParserResult updateCafeteria(final int id, final int week) throws Exception {
    try {
      final CafeteriaInfo cafeteriaInfo = this.uniInfo.getCafeterias().get(id);

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
      final Document document = Jsoup.connect(url).get();
      return new CafeteriaParserResult(this.extractInformations(document, week), document.html());
    } catch (final IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("unknown cafeteria, id=" + id);
    }

  }

  protected abstract Cafeteria extractInformations(Document document, int week) throws Exception;

  @Override
  public void setUniversityInfo(final UniversityInfo universityInfo) {
    this.uniInfo = universityInfo;
  }
}
