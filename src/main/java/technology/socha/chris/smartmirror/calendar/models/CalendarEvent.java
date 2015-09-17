package technology.socha.chris.smartmirror.calendar.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CalendarEvent implements Comparable<CalendarEvent> {

    private String summary;
    private LocalDateTime date;

    public CalendarEvent(String summary, LocalDateTime startingTime){
        this.summary = summary;
        this.date = startingTime;
    }

    @SuppressWarnings("unused")
    private CalendarEvent() {
        /* For Jackson */
    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @JsonProperty("date")
    public String getFormattedDate(){
        return date.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public int compareTo(CalendarEvent o) {
        return date.compareTo(o.getDate());
    }
}
