package org.jboss.pnc.grogu.queue;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.pnc.grogu.util.Processor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class KafkaWorker {

    @Inject
    Processor processor;

    /**
     * Invoked automatically, so nothing else to do
     *
     * @param uuid
     * @throws Exception
     */
    @Incoming("grogu-queue")
    @Transactional
    public void process(UUID uuid) throws Exception {
        Log.debug("Running from kafka worker");
        processor.execute(uuid);
    }
}
