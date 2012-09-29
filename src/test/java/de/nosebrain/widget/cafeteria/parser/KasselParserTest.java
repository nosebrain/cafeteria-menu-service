package de.nosebrain.widget.cafeteria.parser;

import static junit.framework.Assert.assertEquals;
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
    final InputStream inputStream = WuerzburgParserTest.class.getClassLoader().getResourceAsStream("kassel/34-1.html");
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
}
