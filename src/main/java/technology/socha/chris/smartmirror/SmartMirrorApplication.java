package technology.socha.chris.smartmirror;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;
import technology.socha.chris.smartmirror.calendar.resources.CalendarResource;
import technology.socha.chris.smartmirror.calendar.services.GoogleCalendarService;
import technology.socha.chris.smartmirror.travel.configuration.TflConfiguration;
import technology.socha.chris.smartmirror.travel.gateways.TflGateway;
import technology.socha.chris.smartmirror.travel.resources.TravelResource;

import java.io.File;
import java.time.Clock;

public class SmartMirrorApplication extends Application<SmartMirrorConfiguration> {

    public static void main(String[] args) throws Exception {
        new SmartMirrorApplication().run(args);
    }

    @Override
    public String getName() {
        return "smart-mirror";
    }

    @Override
    public void initialize(Bootstrap<SmartMirrorConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));
        bootstrap.addBundle(new Java8Bundle());
    }

    @Override
    public void run(SmartMirrorConfiguration configuration, Environment environment) {
        JerseyClient client = JerseyClientBuilder.createClient();

        File credentialsDir = new File(System.getProperty("user.home"), ".credentials/smart-mirror");
        GoogleCalendarService calendarService = new GoogleCalendarService("Smart Mirror", credentialsDir, configuration.getGoogleClientSecrets());
        CalendarResource calendarResource = new CalendarResource(calendarService, Clock.systemUTC());
        environment.jersey().register(calendarResource);

        TflConfiguration tflConfiguration = configuration.getTflConfiguration();
        JerseyWebTarget target = client.target(tflConfiguration.getEndpoint());
        String appId = tflConfiguration.getAppId();
        String appKey = tflConfiguration.getAppKey();
        TravelResource travelResource = new TravelResource(new TflGateway(target, appId, appKey));
        environment.jersey().register(travelResource);
    }

}
