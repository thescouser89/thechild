package org.jboss.pnc.grogu.util;

import io.quarkus.logging.Log;
import lombok.Setter;
import org.jboss.pnc.grogu.entity.CancelledProcess;
import org.jboss.pnc.grogu.entity.Job;
import org.jboss.pnc.grogu.queue.SimpleLinkedBlockingQueue;
import org.jboss.pnc.grogu.queue.UUIDQueue;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class Processor {

    // Default queue is SimpleLinkedBlockingQueue. You can replace it with setter and getter.
    @Setter
    UUIDQueue queue = new SimpleLinkedBlockingQueue();

    public static final int MAX_DELAY_SECONDS = 600;
    public static final int MAX_RETRIES = 15;

    @Transactional
    public void execute(UUID uuid) throws Exception {

        String uuidString = uuid.toString();
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
                enqueue(nextJob.id);
            }
        } catch (Exception e) {
            // if exception thrown, retry up to a limit
            job.retries++;
            Log.info("Retries happening: " + job.retries);

            if (job.retries < MAX_RETRIES) {
                enqueue(job.id, calculateDelay(job.retries));
            }
        }
    }

    public void test(UUID uuid) {
        Log.info("uuid being processed: " + uuid);
    }

    public void enqueue(UUID uuid) {
        // no delays for the enqueue
        enqueue(uuid, Duration.ZERO);
    }

    public void enqueue(UUID uuid, Duration delay) {
        // TODO: delay not supported haa
        queue.enqueue(uuid);
    }

    public static Duration calculateDelay(int retries) {
        double delaySeconds = Math.pow(1.5, retries);
        return Duration.ofSeconds((long) Math.max(delaySeconds, MAX_DELAY_SECONDS));
    }
}
