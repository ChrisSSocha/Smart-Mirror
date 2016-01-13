package technology.socha.chris.smartmirror.scheduler;

import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

public class TurnScreenOn implements Job{

    public static final JobKey JOB_NAME = JobKey.jobKey("ON");
    public static final TriggerKey TRIGGER_KEY = TriggerKey.triggerKey("ON");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(DateTime.now().toString() + " - Turning screen on");
    }
}
