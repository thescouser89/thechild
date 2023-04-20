package org.jboss.pnc.grogu.queue;

import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Simple implementation for UUIDQueue
 *
 * Should not be used when the service is running with multiple replicas
 */
@ApplicationScoped
public class SimpleLinkedBlockingQueue implements UUIDQueue {

    Queue<UUID> queue = new LinkedBlockingQueue<>();

    @Override
    public void enqueue(UUID uuid) {
        queue.offer(uuid);
    }

    public UUID pop() {
        return queue.poll();
    }
}
