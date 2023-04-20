package org.jboss.pnc.grogu.queue;

import java.util.UUID;

public interface UUIDQueue {
    public void enqueue(UUID uuid);
}
