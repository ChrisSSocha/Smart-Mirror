package technology.socha.chris.smartmirror.calendar.models;

import io.dropwizard.java8.jersey.params.LocalDateTimeParam;

import javax.ws.rs.QueryParam;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Query {

    @QueryParam("max")
    private Integer max;
    @QueryParam("start")
    private LocalDateTimeParam start;
    @QueryParam("end")
    private LocalDateTimeParam end;
    @QueryParam("orderBy")
    private String orderBy;

    @SuppressWarnings("unused")
    public Query(){
        /* For Jackson */
    }



    public int getMax() {
        int defaultValue = 10;
        return max != null ? max : defaultValue;
    }

    public LocalDateTime getStart() {
        LocalDateTime defaultValue = LocalDate.now().atStartOfDay();
        return start != null ? start.get() : defaultValue;
    }

    public LocalDateTime getEnd() {
        LocalDateTime defaultValue = LocalDate.now().atStartOfDay().plusDays(2);
        return end != null ? end.get() : defaultValue;
    }

    public String getOrderBy() {
        String defaultValue = "startTime";
        return orderBy != null ? orderBy : defaultValue;
    }
}
