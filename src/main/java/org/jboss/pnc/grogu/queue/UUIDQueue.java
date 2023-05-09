package org.jboss.pnc.grogu.queue;

import java.time.Duration;
import java.util.UUID;

public interface UUIDQueue {
    public void enqueue(UUID uuid);

    public void enqueue(UUID uuid, Duration delay);
}
