package technology.socha.chris.smartmirror.calendar.handlers;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.common.collect.Lists;
import technology.socha.chris.smartmirror.calendar.models.CalendarEvent;
import technology.socha.chris.smartmirror.calendar.services.CalendarService;

import javax.ws.rs.ServerErrorException;
import java.io.IOException;
import java.util.List;

public class EventsHandler {

    private final CalendarService calendarService;

    public EventsHandler(CalendarService calendarService){
        this.calendarService = calendarService;
    }

    public List<CalendarEvent> handle(){
        try {
            DateTime now = new DateTime(System.currentTimeMillis());
            Events events = calendarService.getCalendar().events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();

            return formatEvents(items);

        } catch (IOException e) {
            throw new ServerErrorException("Server error", 500, e);
        }

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
}
