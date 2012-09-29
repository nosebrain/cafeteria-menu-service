package de.nosebrain.widget.cafeteria.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.model.Day;
import de.nosebrain.widget.cafeteria.model.Menu;

/**
 * 
 * @author nosebrain
 */
public class KasselParser extends AbstractMenuParser {
	private static final Pattern DATE_PATTERN = Pattern.compile("[0-9]{1,2}\\.[0-9]{1,2}\\.");
	private static final Pattern YEAR_PATTERN = Pattern.compile("[0-9]{4}\\s*");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	
	
	@Override
	protected Cafeteria extractInformations(final Document document, final int week) throws Exception {
		/*
		 * before parsing the file check if the requested week equals week
		 * in file
		 */
		final Elements select = document.select("table tr");
		final String weekString = select.get(1).select("td").get(0).text();
		final Matcher matcher = DATE_PATTERN.matcher(weekString);
		matcher.find();
		
		final Matcher yearMatcher = YEAR_PATTERN.matcher(weekString);
		yearMatcher.find();
		
		final String mondayDate = matcher.group(0) + yearMatcher.group(0);
		final Date date = DATE_FORMAT.parse(mondayDate);
		final Calendar mondayCalendar = Calendar.getInstance();
		mondayCalendar.setTime(date);
		final int weekOfSource = mondayCalendar.get(Calendar.WEEK_OF_YEAR);
		if (weekOfSource != week) {
			return null;
		}
		
		final Cafeteria cafeteria = new Cafeteria();
		for (int i = 0; i < 5; i++) {
			cafeteria.addDay(new Day());
		}
		for (int i = 3; i < 11; i += 2) {
			final Element tr = select.get(i);
			final Element priceTr = select.get(i + 1);
			
			final Elements tds = tr.select("td");
			final Elements priceTds = priceTr.select("td");
			for (int j = 1; j < tds.size(); j++) {
				final Element td = tds.get(j);
				final String description = td.text().replace(" ", "").trim();
				
				if (!description.startsWith("-") && !description.isEmpty()) {
					final Menu menu = new Menu();
					final Element priceTd = priceTds.get(j);
					final String[] pricesString = priceTd.text().split("/");
					for (final String priceString : pricesString) {
						menu.addPrice(cleanPrice(priceString));
					}
					menu.setDescription(description);
					cafeteria.getDays().get(j - 1).addMenu(menu);
				}
			}
		}
		return cafeteria;
	}
}
