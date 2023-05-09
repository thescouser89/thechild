package org.jboss.pnc.grogu.queue.linkedlist;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import org.jboss.pnc.grogu.util.Processor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SimpleLinkedBlockingWorker {

    @Inject
    Processor processor;

    @Inject
    SimpleLinkedBlockingQueue simpleLinkedBlockingQueue;

    @Scheduled(every = "2s")
    @Transactional
    public void process() throws Exception {
        Log.debug("Running from SimpleLinkedBlockingWorker");
        List<UUID> toProcess = new ArrayList<>();

        // batch process up to 3 items at a time
        for (int i = 0; i < 3; i++) {
            UUID uuid = simpleLinkedBlockingQueue.pop();
            if (uuid != null) {
                toProcess.add(uuid);
            } else {
                // nothing else to pop
                break;
            }
        }

        for (UUID uuid : toProcess) {
            processor.execute(uuid);
        }
    }
}
