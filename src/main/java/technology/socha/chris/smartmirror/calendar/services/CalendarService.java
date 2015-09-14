package technology.socha.chris.smartmirror.calendar.services;

import technology.socha.chris.smartmirror.calendar.models.Calendar;

import java.io.IOException;

public interface CalendarService {
    Calendar getCalendar() throws IOException;
}
