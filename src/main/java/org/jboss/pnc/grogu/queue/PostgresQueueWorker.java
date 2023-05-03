package org.jboss.pnc.grogu.queue;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import org.hibernate.LockOptions;
import org.jboss.pnc.grogu.entity.PostgresQueueEntity;
import org.jboss.pnc.grogu.util.Processor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PostgresQueueWorker {

    @Inject
    EntityManager entityManager;

    @Inject
    Processor processor;

    @Scheduled(every = "2s")
    @Transactional
    public void process() throws Exception {
        Log.debug("Running from Postgres worker");
        List<PostgresQueueEntity> list = entityManager
                .createQuery("select p from PostgresQueueEntity p " + "order by p.created", PostgresQueueEntity.class)
                .setMaxResults(3)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .setHint("javax.persistence.lock.timeout", LockOptions.SKIP_LOCKED)
                .getResultList();

        if (list != null && list.size() > 0) {
            for (PostgresQueueEntity queue : list) {
                processor.execute(queue.payload);
                queue.delete();
            }
        }
    }
}
