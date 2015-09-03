package technology.socha.chris.smartmirror;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import io.dropwizard.Configuration;
import technology.socha.chris.smartmirror.travel.configuration.TflConfiguration;

public class SmartMirrorConfiguration extends Configuration {

    @JsonProperty("tflConfiguration")
    private TflConfiguration tflConfiguration;

    @JsonProperty("googleClientSecrets")
    private GoogleClientSecrets.Details googleClientSecrets;

    public TflConfiguration getTflConfiguration() {
        return tflConfiguration;
    }

    public GoogleClientSecrets getGoogleClientSecrets() {
        return new GoogleClientSecrets().setInstalled(googleClientSecrets);
    }
}
