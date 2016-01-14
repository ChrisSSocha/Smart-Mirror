package technology.socha.chris.smartmirror.scheduler.resources;

import io.dropwizard.jersey.PATCH;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import technology.socha.chris.smartmirror.scheduler.TurnScreenOff;
import technology.socha.chris.smartmirror.scheduler.TurnScreenOn;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/scheduler")
public class SchedulerResource {

    private final Scheduler scheduler;

    public SchedulerResource(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @POST
    @Path("/on")
    public void turnScreenOn() throws SchedulerException {
        scheduler.triggerJob(TurnScreenOn.JOB_NAME);
    }

    @POST
    @Path("/off")
    public void turnScreenOff() throws SchedulerException {
        scheduler.triggerJob(TurnScreenOff.JOB_NAME);
    }

    @PATCH
    @Path("/on")
    public void scheduleScreenOn(String cron) throws SchedulerException {
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(TurnScreenOn.TRIGGER_KEY)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();

        scheduler.rescheduleJob(TurnScreenOn.TRIGGER_KEY, trigger);
    }

    @PATCH
    @Path("/off")
    public void scheduleScreenOff(String cron) throws SchedulerException {
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(TurnScreenOff.TRIGGER_KEY)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();

        scheduler.rescheduleJob(TurnScreenOff.TRIGGER_KEY, trigger);
    }
}
