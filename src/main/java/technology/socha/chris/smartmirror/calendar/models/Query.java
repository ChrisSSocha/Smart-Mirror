package technology.socha.chris.smartmirror.calendar.models;

import java.time.LocalDateTime;

public class Query {

    private final int max;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String orderBy;

    public Query(int max, LocalDateTime start, LocalDateTime end, String orderBy){
        this.max = max;
        this.start = start;
        this.end = end;
        this.orderBy = orderBy;
    }

    public int getMax() {
        return max;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
