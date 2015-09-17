package technology.socha.chris.smartmirror.calendar.domain;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import technology.socha.chris.smartmirror.calendar.models.CalendarEvent;
import technology.socha.chris.smartmirror.calendar.models.Query;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GoogleCalendar implements Calendar {

    private final com.google.api.services.calendar.Calendar calendar;

    public GoogleCalendar(com.google.api.services.calendar.Calendar calendar) {
        this.calendar = calendar;
    }

    public List<CalendarEvent> getEvents(List<String> calendarIds, Query query) {
        try {
            List<Event> allEvents = new ArrayList<>();
            for (String calendarId : calendarIds) {
                allEvents.addAll(getEvents(query, calendarId).getItems());
            }

            return formatEvents(allEvents);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Events getEvents(Query query, String calendarId) throws IOException {
        return calendar.events().list(calendarId)
                .setMaxResults(query.getMax())
                .setTimeMin(toDateTime(query.getStart()))
                .setTimeMax(toDateTime(query.getEnd()))
                .setOrderBy(query.getOrderBy())
                .setSingleEvents(true)
                .execute();
    }

    private List<CalendarEvent> formatEvents(List<Event> items) {
        return items.stream().map(i -> new CalendarEvent(i.getSummary(), toLocalDateTime(i.getStart()))).sorted().collect(Collectors.toList());
    }

    private DateTime toDateTime(LocalDateTime dateTime) {
        return DateTime.parseRfc3339(dateTime.format(DateTimeFormatter.ISO_DATE_TIME));
    }

    private LocalDateTime toLocalDateTime(EventDateTime eventDateTime) {
        DateTime dateTime = eventDateTime.getDateTime();
        if (dateTime == null) {
            DateTime date = eventDateTime.getDate();
            return LocalDate.parse(date.toStringRfc3339(), DateTimeFormatter.ISO_DATE).atStartOfDay();
        } else {
            return LocalDateTime.parse(dateTime.toStringRfc3339(), DateTimeFormatter.ISO_DATE_TIME);
        }
    }
}
