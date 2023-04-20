package org.jboss.pnc.grogu.queue;

import io.quarkus.logging.Log;
import org.jboss.pnc.grogu.entity.PostgresQueueEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class PostgresQueue implements UUIDQueue {

    @Override
    @Transactional
    public void enqueue(UUID uuid) {
        PostgresQueueEntity postgresQueueEntity = new PostgresQueueEntity();
        postgresQueueEntity.payload = uuid;
        postgresQueueEntity.persist();
        Log.info("-- listing postgres entity");
        PostgresQueueEntity.<PostgresQueueEntity>listAll().stream().forEach(a -> Log.info(a.payload));
        Log.info("-- end listing postgres entity");
    }
}
