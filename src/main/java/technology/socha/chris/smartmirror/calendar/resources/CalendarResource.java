package technology.socha.chris.smartmirror.calendar.resources;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.common.collect.Lists;
import technology.socha.chris.smartmirror.calendar.models.CalendarEvent;
import technology.socha.chris.smartmirror.calendar.services.CalendarService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("/calendar")
@Produces(MediaType.APPLICATION_JSON)
public class CalendarResource {

    private final CalendarService calendarService;

    public CalendarResource(CalendarService calendarService){
        this.calendarService = calendarService;
    }

    @GET
    @Path("/events")
    public List<CalendarEvent> getEvents() {

        try {
            DateTime now = new DateTime(System.currentTimeMillis());
            Events events = calendarService.getCalendar().events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();


            List<CalendarEvent> calendarEvents = Lists.newArrayList();
            for(Event item : items){

                DateTime date = item.getStart().getDateTime();
                if (date == null) {
                    date = item.getStart().getDate();
                }

                calendarEvents.add(new CalendarEvent(item.getSummary(), date.toString()));
            }

            return calendarEvents;

        } catch (IOException e) {
            throw new ServerErrorException("Server error", 500, e);
        }
    }

}
