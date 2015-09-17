package technology.socha.chris.smartmirror.calendar.domain;

import technology.socha.chris.smartmirror.calendar.models.CalendarEvent;
import technology.socha.chris.smartmirror.calendar.models.Query;

import java.util.List;

public interface Calendar {
    List<CalendarEvent> getEvents(List<String> calendarIds, Query query);
}
