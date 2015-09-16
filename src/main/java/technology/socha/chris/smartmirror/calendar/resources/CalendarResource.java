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
import java.util.Optional;

@Path("/calendar")
@Produces(MediaType.APPLICATION_JSON)
public class CalendarResource {

    private final CalendarService calendarService;
    private final Clock clock;

    public CalendarResource(CalendarService calendarService, Clock clock) {
        this.calendarService = calendarService;
        this.clock = clock;
    }

    @GET
    @Path("/events")
    public List<CalendarEvent> getEvents(
            @QueryParam("max") Optional<Integer> maxParam,
            @QueryParam("start") Optional<LocalDateTimeParam> startParam,
            @QueryParam("end") Optional<LocalDateTimeParam> endParam,
            @QueryParam("orderBy") Optional<String> orderByParam) {
        try {
            Query query = getCalendarQuery(maxParam, startParam, endParam, orderByParam);
            return calendarService.getCalendar().getEvents(query);
        } catch (IOException e) {
            throw new InternalServerErrorException(e);
        }
    }

    private Query getCalendarQuery(
            Optional<Integer> maxParam,
            Optional<LocalDateTimeParam> startParam,
            Optional<LocalDateTimeParam> endParam,
            Optional<String> orderByParam) {
        Integer max = maxParam.orElse(10);
        LocalDateTime start = startParam.isPresent() ? startParam.get().get() : LocalDate.now(clock).atStartOfDay();
        LocalDateTime end = endParam.isPresent() ? endParam.get().get() : LocalDate.now(clock).atStartOfDay().plusDays(1);
        String orderBy = orderByParam.orElse("startTime");
        return new Query(max, start, end, orderBy);
    }

}
