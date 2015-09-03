package technology.socha.chris.smartmirror.calendar.models;

public class CalendarEvent {

    private final String summary;
    private final String date;

    public CalendarEvent(String summary, String startingTime){
        this.summary = summary;
        this.date = startingTime;
    }

    public String getSummary() {
        return summary;
    }

    public String getDate() {
        return date;
    }
}
