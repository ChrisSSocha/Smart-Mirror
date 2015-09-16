package technology.socha.chris.smartmirror.calendar.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CalendarEvent {

    private String summary;
    private String date;

    public CalendarEvent(String summary, String startingTime){
        this.summary = summary;
        this.date = startingTime;
    }

    @SuppressWarnings("unused")
    private CalendarEvent() {
        /* For Jackson */
    }

    public String getSummary() {
        return summary;
    }

    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
