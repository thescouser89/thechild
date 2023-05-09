package org.jboss.pnc.grogu.queue.kafka;

import com.google.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.pnc.grogu.queue.UUIDQueue;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.UUID;

/**
 * UUIDQueue implementation using Kafka. We send the uuid into a queue and #{@link KafkaWorker} running in multiple
 * replicas pick up the UUID to process.
 */
@ApplicationScoped
public class KafkaQueue implements UUIDQueue {

    @Inject
    @Channel("grogu-queue")
    Emitter<UUID> emitter;

    @Override
    public void enqueue(UUID uuid) {
        emitter.send(uuid);
    }

    @Override
    public void enqueue(UUID uuid, Duration delay) {
        // TODO: implement
        emitter.send(uuid);
    }
}
