package technology.socha.chris.smartmirror.travel.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TflConfiguration {

    @JsonProperty("endpoint")
    private String endpoint;
    @JsonProperty("app_id")
    private String appId;
    @JsonProperty("app_key")
    private String appKey;

    @SuppressWarnings("unused")
    private TflConfiguration(){
        /* For Jackson */
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppKey() {
        return appKey;
    }
}
