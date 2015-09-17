package technology.socha.chris.smartmirror.calendar.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CalendarConfiguration {

    @JsonProperty("calendarIds")
    private List<String> calendarIds;

    @SuppressWarnings("unused")
    private CalendarConfiguration() {
        /* For Jackson */
    }

    public List<String> getCalendarIds() {
        return calendarIds;
    }
}
