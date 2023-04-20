package org.jboss.pnc.grogu;

import io.quarkus.logging.Log;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.jboss.pnc.grogu.baby.Init;
import org.jboss.pnc.grogu.entity.Job;
import org.jboss.pnc.grogu.queue.*;
import org.jboss.pnc.grogu.util.Processor;
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
    SimpleLinkedBlockingQueue simpleLinkedBlockingQueue;

    @Inject
    KafkaQueue kafkaQueue;

    @Inject
    Processor processor;

    @Inject
    PostgresQueueWorker postgresQueueExecutor;

    @Inject
    PostgresQueue postgresQueue;

    @Test
    void firstTestSimpleLinkedBlockingQueue() throws Exception {

        Job job = Job.getInitJob("1234").get();
        processor.setQueue(simpleLinkedBlockingQueue);
        processor.enqueue(job.id);
        Thread.sleep(20000);

        List<Job> allPersons = Job.listAll();
        for (Job jobTemp : allPersons) {
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
    void firstTestKafkaQueue() throws Exception {

        Job job = Job.getInitJob("1234").get();
        processor.setQueue(kafkaQueue);
        processor.enqueue(job.id);
        Thread.sleep(10000);
        List<Job> allPersons = Job.listAll();
        for (Job jobTemp : allPersons) {
            Log.info(jobTemp.id);
            Log.info(jobTemp.stateClass);
            Log.info(jobTemp.created);
            Log.info(jobTemp.updated);
            Log.info(jobTemp.retries);
            Log.info("-----------------");
        }
    }

    @Test
    void firstTestPostgresQueue() throws Exception {

        Job job = Job.getInitJob("1234").get();
        processor.setQueue(postgresQueue);
        processor.enqueue(job.id);
        Log.info("Sleeping");
        Thread.sleep(20000);
        Log.info("Done");
        List<Job> allPersons = Job.listAll();
        for (Job jobTemp : allPersons) {
            Log.info(jobTemp.id);
            Log.info(jobTemp.stateClass);
            Log.info(jobTemp.created);
            Log.info(jobTemp.updated);
            Log.info(jobTemp.retries);
            Log.info("-----------------");
        }
    }
}
