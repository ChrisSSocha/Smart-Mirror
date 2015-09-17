package technology.socha.chris.smartmirror.calendar.resources;

import io.dropwizard.java8.jersey.params.LocalDateTimeParam;
import technology.socha.chris.smartmirror.calendar.models.CalendarEvent;
import technology.socha.chris.smartmirror.calendar.models.Query;
import technology.socha.chris.smartmirror.calendar.services.CalendarService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Path("/calendar")
@Produces(MediaType.APPLICATION_JSON)
public class CalendarResource {

    private final CalendarService calendarService;
    private final List<String> calendarIds;
    private final Clock clock;

    public CalendarResource(CalendarService calendarService, List<String> calendarIds, Clock clock) {
        this.calendarService = calendarService;
        this.calendarIds = calendarIds;
        this.clock = clock;
    }

    @GET
    @Path("/events")
    public List<CalendarEvent> getEvents(
            @QueryParam("max") @DefaultValue("10") Integer maxParam,
            @QueryParam("orderBy") @DefaultValue("startTime") String orderByParam,
            @QueryParam("start") LocalDateTimeParam startParam,
            @QueryParam("end") LocalDateTimeParam endParam) {
        try {

            LocalDate now = LocalDate.now(clock);
            LocalDateTime startOfDay = now.atStartOfDay();
            LocalDateTime tomorrow = startOfDay.plusDays(1);

            LocalDateTime start = getLocalDateTimeOrDefault(startParam, startOfDay);
            LocalDateTime end = getLocalDateTimeOrDefault(endParam, tomorrow);

            Query query = new Query(maxParam, start, end, orderByParam);
            return calendarService.getCalendar().getEvents(calendarIds, query);
        } catch (IOException e) {
            throw new InternalServerErrorException(e);
        }
    }

    private LocalDateTime getLocalDateTimeOrDefault(LocalDateTimeParam dateTimeParam, LocalDateTime defaultValue) {
        return dateTimeParam != null ? dateTimeParam.get() : defaultValue;
    }

}
