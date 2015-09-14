package technology.socha.chris.smartmirror.calendar.resources;

import technology.socha.chris.smartmirror.calendar.handlers.EventsHandler;
import technology.socha.chris.smartmirror.calendar.models.CalendarEvent;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/calendar")
@Produces(MediaType.APPLICATION_JSON)
public class CalendarResource {

    private final EventsHandler eventsHandler;

    public CalendarResource(EventsHandler eventsHandler){
        this.eventsHandler = eventsHandler;
    }

    @GET
    @Path("/events")
    public List<CalendarEvent> getEvents() {
        return eventsHandler.handle();
    }

}
