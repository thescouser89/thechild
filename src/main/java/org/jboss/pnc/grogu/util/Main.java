package org.jboss.pnc.grogu.util;

import io.quarkus.logging.Log;
import org.jboss.pnc.grogu.model.CancelledProcess;
import org.jboss.pnc.grogu.model.Job;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

public class Main {
    public static int MAX_DELAY_SECONDS = 600;

    public static void main(String[] args) throws Exception {
        // get request
        execute("1234");
    }

    public static void execute(String uuidString) throws Exception {
        int maxRetries = 15;

        Optional<Job> optionalJob = Job.getJobWithUUID(uuidString);

        if (optionalJob.isEmpty()) {
            // log and stop processing
            Log.errorf("Something is wrong: can't find a job with uuid: %s", uuidString);
            return;
        }

        Job job = optionalJob.get();
        ProcessState state = job.toProcessState();

        boolean isCancelled = CancelledProcess.isCancelled(job.processId);

        try {
            Optional<ProcessState> optionalNextState = isCancelled ? state.onCancel() : state.processAndNextState();

            if (optionalNextState.isPresent()) {
                Job nextJob = Job.newJobFromProcessState(job.processId, optionalNextState.get());
                nextJob.persist();
                enqueueMe(nextJob.id);
            }
        } catch (Exception e) {
            // if exception thrown, retry up to a limit
            job.retries++;
            job.persist();
            if (job.retries < maxRetries) {
                enqueueMe(job.id, calculateDelay(job.retries));
            }
        }
    }

    public static void enqueueMe(UUID uuid) {
        // no delays for the enqueue
        enqueueMe(uuid, Duration.ZERO);
    }

    public static void enqueueMe(UUID uuid, Duration delay) {
        // do nothing
        // jobrunr.enqueue(() -> execute(uuid));
        // or
        // kafka.send(uuid);
    }

    public static Duration calculateDelay(int retries) {
        double delaySeconds = Math.pow(1.5, retries);
        return Duration.ofSeconds((long) Math.max(delaySeconds, MAX_DELAY_SECONDS));
    }
}
