package de.nosebrain.widget.cafeteria.parser;

import static de.nosebrain.util.ValidationUtils.present;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
        /*
         * set week start and end
         */
        final DateTime dateTime = new DateTime();
        final DateTime weekStart = dateTime.withWeekOfWeekyear(requestedWeek).withDayOfWeek(1).withTimeAtStartOfDay();
        final DateTime weekEnd = dateTime.withWeekOfWeekyear(requestedWeek).withDayOfWeek(5).withTimeAtStartOfDay();
        cafeteria.setWeekStart(weekStart.toDate());
        cafeteria.setWeekEnd(weekEnd.toDate());
        
        final Elements dayElements = weekElement.select(".day");
        if (dayElements.size() == 0) {
          cafeteria.setClosed(true);
        } else {
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
            
            final Set<String> information = new HashSet<String>();
            final Elements menuElements = dayElement.select(".menu");
            for (final Element menuElement : menuElements) {
              final Menu menu = new Menu();
              final String text = menuElement.select(".title").text();
              menu.setDescription(text);
              
              final Elements price = menuElement.select(".price");
              if ((price.size() == 0) && isMessage(text)) {
                information.add(text);
                continue;
              }
              menu.addPrice(AbstractMenuParser.cleanPrice(price.attr("data-default")));
              menu.addPrice(AbstractMenuParser.cleanPrice(price.attr("data-bed")));
              menu.addPrice(AbstractMenuParser.cleanPrice(price.attr("data-guest")));
              
              final Elements ingredientElements = menuElement.select(".theicon");
              final LinkedList<String> ingredients = new LinkedList<>();
              menu.setIngredients(ingredients);
              for (final Element ingredientElement : ingredientElements) {
                ingredients.add(ingredientElement.attr("title").replaceAll("ue", "ü"));
              }
              day.addMenu(menu);
            }
            checkDay(day, information);
            cafeteria.addDay(day);
          }
        }
        cafeteria.setFoodInfo(document.select(".content").last().text());
        return cafeteria;
      }
    }
    // could not parse anything
    return null;
  }

  private static void checkDay(final Day day, final Set<String> information) {
    final List<Menu> menu = day.getFood();
    if (!present(menu)) {
      day.setHoliday(true);
      if (information.size() == 1) {
        day.setMessage(information.iterator().next());
      }
    } else if (menu.size() == 1) {
      final Menu firstMenu = menu.get(0);
      final String description = firstMenu.getDescription();
      if (isMessage(description)) {
        day.setHoliday(true);
        day.setMessage(description);
      }
    }
  }
  
  private static boolean isMessage(final String message) {
    return message.contains("findet") || message.contains("öffnet") || message.contains("ausflug") || message.contains("geschlossen");
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
