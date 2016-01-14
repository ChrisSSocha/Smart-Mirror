package technology.socha.chris.smartmirror.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

import java.io.IOException;

public class TurnScreenOff implements Job {

    public static final JobKey JOB_NAME = JobKey.jobKey("OFF");
    public static final TriggerKey TRIGGER_KEY = TriggerKey.triggerKey("OFF");

    private static final String TVSERVICE = "/opt/vc/bin/tvservice";

    ProcessBuilder powerOnPreferred = new ProcessBuilder(TVSERVICE, "-p");
    ProcessBuilder powerOffDisplay = new ProcessBuilder(TVSERVICE, "-o");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            powerOnPreferred.start().waitFor();
            powerOffDisplay.start().waitFor();
        } catch (IOException e) {
            throw new JobExecutionException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
