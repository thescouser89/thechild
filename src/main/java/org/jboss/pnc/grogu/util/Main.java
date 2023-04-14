package org.jboss.pnc.grogu.util;

import io.quarkus.logging.Log;
import org.jboss.pnc.grogu.model.CancelledProcess;
import org.jboss.pnc.grogu.model.Job;

import java.util.Optional;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        // get request
        execute("1234");
    }

    public static void execute(String uuidString) throws Exception {
        Optional<Job> optionalJob = Job.getJobWithUUID(uuidString);

        if (optionalJob.isEmpty()) {
            // log and stop processing
            Log.errorf("Something is wrong: can't find a job with uuid: %s", uuidString);
            return;
        }

        Job job = optionalJob.get();
        ProcessState state = job.toProcessState();

        boolean isCancelled = CancelledProcess.isCancelled(job.processId);

        Optional<ProcessState> optionalNextState = isCancelled ? state.onCancel() : state.processAndNextState();

        if (optionalNextState.isPresent()) {
            Job nextJob = Job.newJobFromProcessState(job.processId, optionalNextState.get());
            nextJob.persist();
            enqueueMe(nextJob.id);
        }
    }

    public static void enqueueMe(UUID uuid) {
        // do nothing
        // jobrunr.enqueue(() -> execute(uuid));
        // or
        // kafka.send(uuid);
    }
}
