package technology.socha.chris.smartmirror;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import technology.socha.chris.smartmirror.calendar.configuration.CalendarConfiguration;
import technology.socha.chris.smartmirror.travel.configuration.TflConfiguration;

public class SmartMirrorConfiguration extends Configuration {

    @JsonProperty("jerseyClient")
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    @JsonProperty("tflConfiguration")
    private TflConfiguration tflConfiguration;

    @JsonProperty("calendarConfiguration")
    private CalendarConfiguration calendarConfiguration;

    @JsonProperty("googleClientSecrets")
    private GoogleClientSecrets.Details googleClientSecrets;

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClient;
    }

    public TflConfiguration getTflConfiguration() {
        return tflConfiguration;
    }

    public CalendarConfiguration getCalendarConfiguration() {
        return calendarConfiguration;
    }

    public GoogleClientSecrets getGoogleClientSecrets() {
        return new GoogleClientSecrets().setInstalled(googleClientSecrets);
    }
}
