package de.nosebrain.widget.cafeteria.parser;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import java.io.InputStream;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.model.Day;
import de.nosebrain.widget.cafeteria.model.Menu;

public class KasselParserTest {
  private static final KasselParser PARSER = new KasselParser();

  @Test
  public void test34_1() throws Exception {
    final InputStream inputStream = KasselParserTest.class.getClassLoader().getResourceAsStream("kassel/34-1.html");
    final Document document = Jsoup.parse(inputStream, "iso-8859-1", "");
    final Cafeteria cafeteria = PARSER.extractInformations(document, 34);
    final List<Day> days = cafeteria.getDays();
    assertEquals(5, days.size());

    final Day mo = days.get(0);
    final List<Menu> moFood = mo.getFood();
    assertEquals(3, moFood.size());
    assertEquals("Chinesische Gemüsepfanne mit Basmatireis (v)", moFood.get(0).getDescription());

    final Cafeteria cafeteriaInOneWeek = PARSER.extractInformations(document, 35);
    assertNull(cafeteriaInOneWeek);
  }
  
  @Test
  public void christmas2012() throws Exception {
    final InputStream inputStream = KasselParserTest.class.getClassLoader().getResourceAsStream("kassel/weihnachten_2012.html");
    final Document document = Jsoup.parse(inputStream, "iso-8859-1", "");
    final Cafeteria cafeteria = PARSER.extractInformations(document, 50);
    final List<Day> days = cafeteria.getDays();
    assertEquals(5, days.size());

    final Day mo = days.get(0);
    final List<Menu> moFood = mo.getFood();
    assertEquals(3, moFood.size());
    assertEquals("Rührei mit Blattspinat (f)", moFood.get(0).getDescription());
    assertNotNull(cafeteria.getWeekEnd());
    assertNotNull(cafeteria.getWeekStart()); // TODO: test more
    assertEquals("(1) mit Farbstoff, (2) mit Konservierungsstoff, (3) mit Antioxidationsmittel, (4) mit Geschmacksverstärker, (5) geschwefelt, (6) geschwärzt, (7) gewachst, (8) mit Phosphat, (9) mit Süßungsmitteln, (9a) mit einer Zuckerart und Süßungsmitteln, (10) mit einer Phenylalaninquelle, (f) fleischlos, (v) vegan, (ö) ökologisch, DE-Öko-034, (s) Schweinefleisch bzw. Schweinefl.-Anteile, (r) Rindfleisch bzw. Rindfl.-Anteile, (a) mit Alkohol, (g) mit Gelatine. Wir verwenden ausschl. jodiertes Speisesalz. ", cafeteria.getFoodInfo());
  }
  
  @Test
  public void centralCaf() throws Exception {
    final InputStream inputStream = KasselParserTest.class.getClassLoader().getResourceAsStream("kassel/central_caf.html");
    final Document document = Jsoup.parse(inputStream, "iso-8859-1", "");
    final Cafeteria cafeteria = PARSER.extractInformations(document, 7);
    final List<Day> days = cafeteria.getDays();
    assertEquals(5, days.size());
    
    final Day mo = days.get(0);
    final List<Menu> moFood = mo.getFood();
    assertEquals(5, moFood.size());
  }
}
