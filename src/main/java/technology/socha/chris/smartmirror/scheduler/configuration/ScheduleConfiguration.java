package technology.socha.chris.smartmirror.scheduler.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScheduleConfiguration {

    @JsonProperty("turnOnSchedule")
    private String turnOnSchedule;

    @JsonProperty("turnOffSchedule")
    private String turnOffSchedule;

    @SuppressWarnings("unused")
    private ScheduleConfiguration(){
        /* For Jackson */
    }

    public String getTurnOnSchedule() {
        return turnOnSchedule;
    }

    public String getTurnOffSchedule() {
        return turnOffSchedule;
    }
}
