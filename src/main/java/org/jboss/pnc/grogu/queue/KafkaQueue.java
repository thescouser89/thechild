package org.jboss.pnc.grogu.queue;

import com.google.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

/**
 * UUIDQueue implementation using Kafka. We send the uuid into a queue and #{@link org.jboss.pnc.grogu.queue.KafkaExecutor}
 * running in multiple replicas pick up the UUID to process.
 */
@ApplicationScoped
public class KafkaQueue implements UUIDQueue {

    @Inject
    @Channel("price-create")
    Emitter<UUID> emitter;

    @Override
    public void enqueue(UUID uuid) {
        emitter.send(uuid);
    }
}
