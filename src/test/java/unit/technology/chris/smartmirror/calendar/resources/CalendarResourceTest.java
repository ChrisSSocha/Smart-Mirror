package unit.technology.chris.smartmirror.calendar.resources;

import io.dropwizard.java8.jersey.OptionalMessageBodyWriter;
import io.dropwizard.java8.jersey.OptionalParamFeature;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import technology.socha.chris.smartmirror.calendar.domain.GoogleCalendar;
import technology.socha.chris.smartmirror.calendar.models.CalendarEvent;
import technology.socha.chris.smartmirror.calendar.models.Query;
import technology.socha.chris.smartmirror.calendar.resources.CalendarResource;
import technology.socha.chris.smartmirror.calendar.services.CalendarService;

import javax.ws.rs.core.GenericType;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.mockito.Mockito.*;

public class CalendarResourceTest {

    public static final String LOCAL_DATE_TIME_NOW = "2007-12-03T10:15:30.00Z";
    public static final ZoneId TIMEZONE = ZoneId.of("Z");

    public static final Integer DEFAULT_MAX = 10;
    public static final String DEFAULT_START = "2007-12-03T00:00";
    public static final String DEFAULT_END = "2007-12-04T00:00";
    public static final String DEFAULT_ORDER = "startTime";

    private CalendarService calendarService = mock(CalendarService.class);
    private GoogleCalendar calendar = mock(GoogleCalendar.class);
    private Clock clock = Clock.fixed(Instant.parse(LOCAL_DATE_TIME_NOW), TIMEZONE);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new CalendarResource(calendarService, clock))
            .addProvider(OptionalMessageBodyWriter.class)
            .addProvider(OptionalParamFeature.class)
            .build();

    @Before
    public void beforeEach() throws Exception {
        when(calendarService.getCalendar()).thenReturn(calendar);
    }

    @Test
    public void shouldUseDefaultQuery() throws Exception {
        resources.client()
                .target("/calendar/events")
                .request()
                .get(new GenericType<List<CalendarEvent>>() {
                });

        Query query = new Query(
                DEFAULT_MAX,
                LocalDateTime.parse(DEFAULT_START),
                LocalDateTime.parse(DEFAULT_END),
                DEFAULT_ORDER);

        verify(calendar).getEvents(query);
    }

    @Test
    public void shouldUseMaxQueryParam() throws Exception {
        resources.client()
                .target("/calendar/events")
                .queryParam("max", 12)
                .request()
                .get(new GenericType<List<CalendarEvent>>() {
                });

        Query query = new Query(
                12,
                LocalDateTime.parse(DEFAULT_START),
                LocalDateTime.parse(DEFAULT_END),
                DEFAULT_ORDER);

        verify(calendar).getEvents(query);
    }

    @Test
    public void shouldUseStartQueryParam() throws Exception {
        String startTime = "2008-12-03T23:23";

        resources.client()
                .target("/calendar/events")
                .queryParam("start", startTime)
                .request()
                .get(new GenericType<List<CalendarEvent>>() {
                });

        Query query = new Query(
                DEFAULT_MAX,
                LocalDateTime.parse(startTime),
                LocalDateTime.parse(DEFAULT_END),
                DEFAULT_ORDER);

        verify(calendar).getEvents(query);
    }

    @Test
    public void shouldUseStartEndParam() throws Exception {
        String endTime = "2008-12-03T23:23";

        resources.client()
                .target("/calendar/events")
                .queryParam("end", endTime)
                .request()
                .get(new GenericType<List<CalendarEvent>>() {
                });

        Query query = new Query(
                DEFAULT_MAX,
                LocalDateTime.parse(DEFAULT_START),
                LocalDateTime.parse(endTime),
                DEFAULT_ORDER);


        verify(calendar).getEvents(query);
    }

    @Test
    public void shouldUseOrderByQueryParam() throws Exception {
        resources.client()
                .target("/calendar/events")
                .queryParam("orderBy", "someThing")
                .request()
                .get(new GenericType<List<CalendarEvent>>() {
                });

        Query query = new Query(
                DEFAULT_MAX,
                LocalDateTime.parse(DEFAULT_START),
                LocalDateTime.parse(DEFAULT_END),
                "someThing");

        verify(calendar).getEvents(query);
    }

}
