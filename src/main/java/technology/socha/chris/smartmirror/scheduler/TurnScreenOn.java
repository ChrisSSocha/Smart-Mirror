package technology.socha.chris.smartmirror.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

import java.io.IOException;

public class TurnScreenOn implements Job {

    public static final JobKey JOB_NAME = JobKey.jobKey("ON");
    public static final TriggerKey TRIGGER_KEY = TriggerKey.triggerKey("ON");

    private static final String TVSERVICE = "/opt/vc/bin/tvservice";
    private static final String CHVT = "chvt";

    private final ProcessBuilder powerOnPreferred = new ProcessBuilder(TVSERVICE, "-p");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            powerOnPreferred.start().waitFor();
            changeVirtualTerminal(1).start().waitFor();
            changeVirtualTerminal(7).start().waitFor();
        } catch (IOException e) {
            throw new JobExecutionException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ProcessBuilder changeVirtualTerminal(int terminalNumber) {
        return new ProcessBuilder(CHVT, String.valueOf(terminalNumber));
    }
}
