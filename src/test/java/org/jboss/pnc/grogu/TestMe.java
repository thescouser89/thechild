package org.jboss.pnc.grogu;

import io.quarkus.logging.Log;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.jboss.pnc.grogu.baby.Init;
import org.jboss.pnc.grogu.model.Job;
import org.jboss.pnc.grogu.util.SimpleQueueProcessor;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class TestMe {

    @Transactional
    @Test
    public void firstTest() throws Exception {
        Init init = new Init();
        Job job = Job.newJobFromProcessState("1234", init);
        job.persist();

        Optional<Job> job2 = Job.getJobWithUUID(job.id.toString());
        Log.info(job2.get().stateClass);
    }

}
