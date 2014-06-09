package de.nosebrain.widget.cafeteria.parser;

import static de.nosebrain.util.ValidationUtils.present;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.model.Day;
import de.nosebrain.widget.cafeteria.model.Menu;

/**
 * TODO: rename? also works for Bamberg, …
 * 
 * @author nosebrain
 */
public class WuerzburgParser extends AbstractMenuParser {

  @Override
  protected Cafeteria extractInformations(final Document document, final int requestedWeek) throws Exception {
    final Cafeteria cafeteria = new Cafeteria();
    final Elements weeks = document.select(".mensamenu .week");
    
    for (final Element weekElement : weeks) {
      final String week = weekElement.attr("data-kw").trim().substring(2);
      
      if (Integer.parseInt(week) == requestedWeek) {
        final Elements dayElements = weekElement.select(".day");
        if (dayElements.size() == 0) {
          cafeteria.setClosed(true);
        } else {
          /*
           * set week start and end
           */
          final DateTime dateTime = new DateTime();
          final DateTime weekStart = dateTime.withWeekOfWeekyear(requestedWeek).withDayOfWeek(1).withTimeAtStartOfDay();
          final DateTime weekEnd = dateTime.withWeekOfWeekyear(requestedWeek).withDayOfWeek(5).withTimeAtStartOfDay();
          cafeteria.setWeekStart(weekStart.toDate());
          cafeteria.setWeekEnd(weekEnd.toDate());
          
          for (final Element dayElement : dayElements) {
            final Day day = new Day();
            final String date = dayElement.select("h5").text();
            final String[] split = date.split(" ");
            // check for holiday
            final int dayIndex = getDay(split[0]);
            while (dayIndex != cafeteria.getDays().size()) {
              final Day holiday = new Day();
              holiday.setHoliday(true);
              cafeteria.addDay(holiday);
            }
            
            final Set<String> informations = new HashSet<String>();
            final Elements menuElements = dayElement.select(".menu");
            for (final Element menuElement : menuElements) {
              final Menu menu = new Menu();
              final String text = menuElement.select(".title").text();
              menu.setDescription(text);
              
              final Elements price = menuElement.select(".price");
              if (price.size() == 0) {
                informations.add(text);
                continue;
              }
              menu.addPrice(AbstractMenuParser.cleanPrice(price.attr("data-default")));
              menu.addPrice(AbstractMenuParser.cleanPrice(price.attr("data-bed")));
              menu.addPrice(AbstractMenuParser.cleanPrice(price.attr("data-guest")));
              // TODO: kind
              // TODO: each
              day.addMenu(menu);
            }
            checkDay(day, informations);
            cafeteria.addDay(day);
          }
        }
        cafeteria.setFoodInfo(document.select(".content").last().text());
        return cafeteria;
      }
    }
    // cound not parse anything
    return null;
  }

  private static void checkDay(final Day day, final Set<String> informations) {
    if (!present(day.getFood())) {
      day.setHoliday(true);
      if (informations.size() == 1) {
        day.setMessage(informations.iterator().next());
      }
    }
  }

  private static int getDay(final String string) {
    if ("Montag".equals(string)) {
      return 0;
    }
    
    if ("Dienstag".equals(string)) {
      return 1;
    }
    
    if ("Mittwoch".equals(string)) {
      return 2;
    }
    
    if ("Donnerstag".equals(string)) {
      return 3;
    }
    
    if ("Freitag".equals(string)) {
      return 4;
    }
    throw new IllegalArgumentException(string);
  }
}
