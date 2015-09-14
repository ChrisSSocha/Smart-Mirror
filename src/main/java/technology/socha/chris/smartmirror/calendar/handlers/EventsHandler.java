package technology.socha.chris.smartmirror.calendar.handlers;

import technology.socha.chris.smartmirror.calendar.models.CalendarEvent;
import technology.socha.chris.smartmirror.calendar.models.Query;
import technology.socha.chris.smartmirror.calendar.models.QueryBuilder;
import technology.socha.chris.smartmirror.calendar.services.CalendarService;

import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.util.List;

public class EventsHandler {

    private final CalendarService calendarService;

    public EventsHandler(CalendarService calendarService){
        this.calendarService = calendarService;
    }

    public List<CalendarEvent> handle(){
        try {
            Query build = new QueryBuilder().build();
            return calendarService.getCalendar().getEvents(build);
        } catch (IOException e) {
            throw new InternalServerErrorException(e);
        }
    }
}
