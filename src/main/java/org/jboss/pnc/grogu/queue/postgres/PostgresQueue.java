package org.jboss.pnc.grogu.queue.postgres;

import io.quarkus.logging.Log;
import org.jboss.pnc.grogu.entity.PostgresQueueEntity;
import org.jboss.pnc.grogu.queue.UUIDQueue;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.Duration;
import java.util.UUID;

@ApplicationScoped
public class PostgresQueue implements UUIDQueue {

    @Override
    @Transactional
    public void enqueue(UUID uuid) {
        PostgresQueueEntity postgresQueueEntity = new PostgresQueueEntity();
        postgresQueueEntity.payload = uuid;
        postgresQueueEntity.persist();
    }

    @Override
    @Transactional
    public void enqueue(UUID uuid, Duration delay) {
        // TODO: implement
        enqueue(uuid);
    }
}
