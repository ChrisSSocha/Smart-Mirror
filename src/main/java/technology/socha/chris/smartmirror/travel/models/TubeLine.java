package technology.socha.chris.smartmirror.travel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TubeLine {

    @JsonProperty("name")
    private String name;
    @JsonProperty("lineStatuses")
    private List<TubeStatus> lineStatuses;

    public TubeLine(String name, List<TubeStatus> lineStatuses) {
        this.name = name;
        this.lineStatuses = lineStatuses;
    }

    @SuppressWarnings("unused")
    private TubeLine(){
        /* For jackson */
    }

    public String getName() {
        return name;
    }

    public List<TubeStatus> getLineStatuses() {
        return lineStatuses;
    }
}
