package technology.socha.chris.smartmirror.calendar.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;

public class Query {

    private Integer max;
    private LocalDateTime start;
    private LocalDateTime end;
    private String orderBy;

    @SuppressWarnings("unused")
    private Query(){
        /* For Jackson */
    }

    public Query(Integer max, LocalDateTime start, LocalDateTime end, String orderBy) {
        this.max = max;
        this.start = start;
        this.end = end;
        this.orderBy = orderBy;
    }

    public Integer getMax() {
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

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(o, this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
