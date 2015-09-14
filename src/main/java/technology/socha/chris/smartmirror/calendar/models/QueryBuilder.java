package technology.socha.chris.smartmirror.calendar.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class QueryBuilder {

    private int max = 10;
    private LocalDateTime start = LocalDate.now().atStartOfDay();
    private LocalDateTime end = start.plusDays(2);
    private String orderBy = "startTime";

    public QueryBuilder withMax(int max){
        this.max = max;
        return this;
    }

    public QueryBuilder withStart(LocalDateTime start){
        this.start = start;
        return this;
    }

    public QueryBuilder withEnd(LocalDateTime end){
        this.end = end;
        return this;
    }

    public QueryBuilder withOrder(String orderBy){
        this.orderBy = orderBy;
        return this;
    }

    public Query build(){
        return new Query(max, start, end, orderBy);
    }


}
