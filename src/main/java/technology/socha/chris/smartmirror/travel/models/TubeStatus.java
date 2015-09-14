package technology.socha.chris.smartmirror.travel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TubeStatus {

    @JsonProperty("statusSeverity")
    private int severity;
    @JsonProperty("statusSeverityDescription")
    private String description;
    @JsonProperty("reason")
    private String message;

    public TubeStatus(int severity, String description, String message) {
        this.severity = severity;
        this.description = description;
        this.message = message;
    }

    @SuppressWarnings("unused")
    private TubeStatus(){
        /* For Jackson */
    }

    public int getSeverity() {
        return severity;
    }

    public String getDescription() {
        return description;
    }

    public String getMessage() {
        return message;
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
