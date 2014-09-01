package de.nosebrain.widget.cafeteria.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.model.Day;
import de.nosebrain.widget.cafeteria.model.Menu;

public class WuerzburgParserTest {

  private static final WuerzburgParser PARSER = new WuerzburgParser();

  @Test
  public void test33_5() throws Exception {
    final InputStream inputStream = WuerzburgParserTest.class.getClassLoader().getResourceAsStream("wuerzburg/33-5.html");
    final Document document = Jsoup.parse(inputStream, "UTF-8", "");
    final Cafeteria extractInformations = PARSER.extractInformations(document, 33);
    assertTrue(extractInformations.isClosed());
  }

  @Test
  public void test33_2() throws Exception {
    final InputStream inputStream = WuerzburgParserTest.class.getClassLoader().getResourceAsStream("wuerzburg/33-2.html");
    final Document document = Jsoup.parse(inputStream, "UTF-8", "");
    final Cafeteria extractInformations = PARSER.extractInformations(document, 33);
    assertFalse(extractInformations.isClosed());

    final List<Day> days = extractInformations.getDays();
    assertEquals(5, days.size());

    final Day mon = days.get(0);

    final List<Menu> menu = mon.getFood();
    assertEquals(3, menu.size());

    assertEquals("Hähnchenbrustfilet an Portweinsoße", menu.get(1).getDescription());
    final Day wed = days.get(2);
    assertTrue(wed.isHoliday());
    
    assertEquals("Änderungen möglich! Die Gerichte können sich kurzfristig abändern oder ausverkauft sein. DE-ÖKO-006 (Zertifizierung)", extractInformations.getFoodInfo());
    assertNotNull(extractInformations.getWeekEnd());
    assertNotNull(extractInformations.getWeekStart());
  }
  
  @Test
  public void test2013_26_1() throws Exception {
    final InputStream inputStream = WuerzburgParserTest.class.getClassLoader().getResourceAsStream("wuerzburg/2013-26-1.html");
    final Document document = Jsoup.parse(inputStream, "UTF-8", "");
    final Cafeteria extractInformations = PARSER.extractInformations(document, 26);
    assertFalse(extractInformations.isClosed());

    final List<Day> days = extractInformations.getDays();
    assertEquals(5, days.size());

    final Day mon = days.get(0);

    final List<Menu> menu = mon.getFood();
    assertEquals(6, menu.size());

    assertEquals("Giant Chickenburger mit Steakhousepommes und Coleslaw", menu.get(1).getDescription());
    final Day wed = days.get(2);
    final Menu menuWed5 = wed.getFood().get(4);
    assertEquals("Gnocchigratin mit Tomaten, roten Zwiebln und Gorgonzola", menuWed5.getDescription());
  }
  
  @Test
  public void test2013_26_2() throws Exception {
    final InputStream inputStream = WuerzburgParserTest.class.getClassLoader().getResourceAsStream("wuerzburg/2013-26-2.html");
    final Document document = Jsoup.parse(inputStream, "UTF-8", "");
    final Cafeteria extractInformations = PARSER.extractInformations(document, 26);
    assertFalse(extractInformations.isClosed());

    final List<Day> days = extractInformations.getDays();
    assertEquals(5, days.size());

    final Day mon = days.get(0);

    final List<Menu> menu = mon.getFood();
    assertEquals(3, menu.size());

    assertEquals("Putensteak an Chilidip", menu.get(1).getDescription());
    final Day wed = days.get(2);
    final Menu menuWed5 = wed.getFood().get(2);
    assertEquals("Bohnen-Zucchini-Chili mit Vollkornreis", menuWed5.getDescription());
  }
  
  @Test
  public void test2013_29_1() throws Exception {
    final InputStream inputStream = WuerzburgParserTest.class.getClassLoader().getResourceAsStream("wuerzburg/2013-29-1.html");
    final Document document = Jsoup.parse(inputStream, "UTF-8", "");
    final Cafeteria extractInformations = PARSER.extractInformations(document, 29);
    assertFalse(extractInformations.isClosed());
    for (final Day day : extractInformations.getDays()) {
      final List<Menu> menu = day.getFood();
      assertEquals(6, menu.size());
      assertEquals("Tagliata vom Rind mit Rucola und Parmesan", menu.get(0).getDescription());
    }
  }
  
  @Test
  public void test2013_30_1() throws Exception {
    final InputStream inputStream = WuerzburgParserTest.class.getClassLoader().getResourceAsStream("wuerzburg/2013-29-1.html");
    final Document document = Jsoup.parse(inputStream, "UTF-8", "");
    final Cafeteria extractInformations = PARSER.extractInformations(document, 30);
    
    assertTrue(extractInformations.isClosed());
  }
  
  @Test
  public void test2014_24_1() throws Exception {
    final InputStream inputStream = WuerzburgParserTest.class.getClassLoader().getResourceAsStream("wuerzburg/2014-24-1.html");
    final Document document = Jsoup.parse(inputStream, "UTF-8", "");
    final Cafeteria extractInformations = PARSER.extractInformations(document, 24);
    
    final Day monday = extractInformations.getDays().get(0);
    assertTrue(monday.isHoliday());
    
    final Day tuesday = extractInformations.getDays().get(1);
    assertTrue(tuesday.isHoliday());
  }
  
  @Test
  public void test2014_24_2() throws Exception {
    final InputStream inputStream = WuerzburgParserTest.class.getClassLoader().getResourceAsStream("wuerzburg/2014-24-2.html");
    final Document document = Jsoup.parse(inputStream, "UTF-8", "");
    final Cafeteria extractInformations = PARSER.extractInformations(document, 24);
    
    final Day monday = extractInformations.getDays().get(0);
    assertTrue(monday.isHoliday());
    
    final Day tuesday = extractInformations.getDays().get(1);
    assertTrue(tuesday.isHoliday());
    assertEquals("Betriebsausflug", tuesday.getMessage());
  }
  
  @Test
  public void test2014_36_3() throws Exception {
    final InputStream inputStream = WuerzburgParserTest.class.getClassLoader().getResourceAsStream("wuerzburg/2014-36-3.html");
    final Document document = Jsoup.parse(inputStream, "UTF-8", "");
    final Cafeteria extractInformations = PARSER.extractInformations(document, 36);
    
    final Day monday = extractInformations.getDays().get(0);
    assertEquals(5, monday.getFood().size());
    
    final Day thursday = extractInformations.getDays().get(3);
    assertEquals(3, thursday.getFood().size());
    
    final Day friday = extractInformations.getDays().get(4);
    assertEquals(3, friday.getFood().size());
  }
}
