package technology.socha.chris.smartmirror;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import technology.socha.chris.smartmirror.calendar.resources.CalendarResource;
import technology.socha.chris.smartmirror.calendar.services.GoogleCalendarService;
import technology.socha.chris.smartmirror.travel.configuration.TflConfiguration;
import technology.socha.chris.smartmirror.travel.gateways.TflGateway;
import technology.socha.chris.smartmirror.travel.resources.TravelResource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import java.io.File;
import java.time.Clock;
import java.util.List;

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

        Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration()).build(getName());

        File credentialsDir = new File(System.getProperty("user.home"), ".credentials/smart-mirror");
        GoogleCalendarService calendarService = new GoogleCalendarService("Smart Mirror", credentialsDir, configuration.getGoogleClientSecrets());
        List<String> calendarIds = configuration.getCalendarConfiguration().getCalendarIds();
        CalendarResource calendarResource = new CalendarResource(calendarService, calendarIds, Clock.systemUTC());
        environment.jersey().register(calendarResource);

        TflConfiguration tflConfiguration = configuration.getTflConfiguration();
        WebTarget target = client.target(tflConfiguration.getEndpoint());
        String appId = tflConfiguration.getAppId();
        String appKey = tflConfiguration.getAppKey();
        TravelResource travelResource = new TravelResource(new TflGateway(target, appId, appKey));
        environment.jersey().register(travelResource);
    }

}
