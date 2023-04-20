package org.jboss.pnc.grogu;

import io.quarkus.logging.Log;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.jboss.pnc.grogu.baby.Init;
import org.jboss.pnc.grogu.model.Job;
import org.jboss.pnc.grogu.queue.KafkaQueue;
import org.jboss.pnc.grogu.queue.SimpleLinkedBlockingQueue;
import org.jboss.pnc.grogu.util.Executor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class TestMe {

    @Inject
    KafkaQueue kafkaQueue;

    @Inject
    Executor executor;

    @Test
    @TestTransaction
    void firstTestSimpleLinkedBlockingQueue() throws Exception {

        SimpleLinkedBlockingQueue queue = new SimpleLinkedBlockingQueue();
        executor.setQueue(queue);

        Optional<Job> job2 = Job.getInitJob("1234");
        Log.info(job2.get().stateClass);
        executor.enqueue(job2.get().id);

        while(true) {
            UUID uuid = queue.pop();

            if (uuid == null) {
                break;
            }
            executor.execute(uuid);
        }

        List<Job> allPersons = Job.listAll();
        for (Job jobTemp: allPersons) {
            Log.info(jobTemp.id);
            Log.info(jobTemp.stateClass);
            Log.info(jobTemp.created);
            Log.info(jobTemp.updated);
            Log.info(jobTemp.retries);
            Log.info("-----------------");
        }
    }

    @BeforeAll
    @Transactional
    static void beforeAll() throws Exception {
        Init init = new Init();
        Job job = Job.newJobFromProcessState("1234", init);
        job.persist();
    }

    @Test
    @TestTransaction
    void firstTestKafkaQueue() throws Exception {

        Job job = Job.getInitJob("1234").get();
        executor.setQueue(kafkaQueue);
        executor.enqueue(job.id);
        Thread.sleep(10000);
        List<Job> allPersons = Job.listAll();
        for (Job jobTemp: allPersons) {
            Log.info(jobTemp.id);
            Log.info(jobTemp.stateClass);
            Log.info(jobTemp.created);
            Log.info(jobTemp.updated);
            Log.info(jobTemp.retries);
            Log.info("-----------------");
        }
    }
}
