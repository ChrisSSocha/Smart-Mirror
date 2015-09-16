package technology.socha.chris.smartmirror.calendar.services;

import technology.socha.chris.smartmirror.calendar.domain.GoogleCalendar;

import java.io.IOException;

public interface CalendarService {
    GoogleCalendar getCalendar() throws IOException;
}
