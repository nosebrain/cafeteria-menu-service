package de.nosebrain.widget.cafeteria.parser;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

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
	}
}
