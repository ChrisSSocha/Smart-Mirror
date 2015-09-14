package technology.socha.chris.smartmirror.calendar.models;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Calendar {

    private final com.google.api.services.calendar.Calendar googleCalendar;

    public Calendar(com.google.api.services.calendar.Calendar googleCalendar) {
        this.googleCalendar = googleCalendar;
    }

    public List<CalendarEvent> getEvents(Query query) throws IOException {
        Events events = googleCalendar.events().list("primary")
                .setMaxResults(query.getMax())
                .setTimeMin(toDateTime(query.getStart()))
//                .setTimeMax(toDateTime(query.getEnd()))
                .setOrderBy(query.getOrderBy())
                .setSingleEvents(true)
                .execute();

        return formatEvents(events.getItems());
    }

    private List<CalendarEvent> formatEvents(List<Event> items) {
        List<CalendarEvent> calendarEvents = Lists.newArrayList();
        for(Event item : items){

            DateTime date = item.getStart().getDateTime();
            if (date == null) {
                date = item.getStart().getDate();
            }

            calendarEvents.add(new CalendarEvent(item.getSummary(), date.toString()));
        }
        return calendarEvents;
    }


    private DateTime toDateTime(LocalDateTime dateTime){
        return DateTime.parseRfc3339(dateTime.format(DateTimeFormatter.ISO_DATE_TIME));
    }
}