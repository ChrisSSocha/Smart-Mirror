package technology.socha.chris.smartmirror;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import technology.socha.chris.smartmirror.admin.resources.VersionResource;
import technology.socha.chris.smartmirror.calendar.resources.CalendarResource;
import technology.socha.chris.smartmirror.calendar.services.GoogleCalendarService;
import technology.socha.chris.smartmirror.scheduler.TurnScreenOff;
import technology.socha.chris.smartmirror.scheduler.TurnScreenOn;
import technology.socha.chris.smartmirror.scheduler.resources.SchedulerResource;
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
    public void run(SmartMirrorConfiguration configuration, Environment environment) throws Exception{

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

        environment.jersey().register(new VersionResource());

        Scheduler scheduler = scheduleJobs();

        environment.jersey().register(new SchedulerResource(scheduler));
    }

    private Scheduler scheduleJobs() throws SchedulerException {
        JobDetail turnScreenOnJob = JobBuilder.newJob(TurnScreenOn.class)
                .withIdentity(TurnScreenOn.JOB_NAME)
                .build();

        CronTrigger turnScreenOnTrigger = TriggerBuilder.newTrigger()
                .withIdentity(TurnScreenOn.TRIGGER_KEY)
                .withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ?"))
                .build();

        JobDetail turnScreenOffJob = JobBuilder.newJob(TurnScreenOff.class)
                .withIdentity(TurnScreenOff.JOB_NAME)
                .build();

        CronTrigger turnScreenOffTrigger = TriggerBuilder.newTrigger()
                .withIdentity(TurnScreenOff.TRIGGER_KEY)
                .withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ?"))
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();

        scheduler.scheduleJob(turnScreenOnJob, turnScreenOnTrigger);
        scheduler.scheduleJob(turnScreenOffJob, turnScreenOffTrigger);
        return scheduler;
    }

}
