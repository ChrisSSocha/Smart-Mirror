package technology.socha.chris.smartmirror.travel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TubeStatus {

    @JsonProperty("statusSeverity")
    private int severity;
    @JsonProperty("statusSeverityDescription")
    private String description;

    public TubeStatus(int severity, String description) {
        this.severity = severity;
        this.description = description;
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
}
