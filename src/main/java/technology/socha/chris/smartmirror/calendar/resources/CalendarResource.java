package technology.socha.chris.smartmirror.calendar.resources;

import technology.socha.chris.smartmirror.calendar.models.CalendarEvent;
import technology.socha.chris.smartmirror.calendar.models.Query;
import technology.socha.chris.smartmirror.calendar.services.CalendarService;

import javax.ws.rs.*;
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
    public List<CalendarEvent> getEvents(@BeanParam Query query) {
        try {
            return calendarService.getCalendar().getEvents(query);
        } catch (IOException e) {
            throw new InternalServerErrorException(e);
        }
    }

}
