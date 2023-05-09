package org.jboss.pnc.grogu;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.logging.Log;
import org.jboss.pnc.grogu.entity.Job;
import org.jboss.pnc.grogu.util.ProcessState;
import org.jboss.pnc.grogu.util.ProcessStateWithCallback;
import org.jboss.pnc.grogu.util.Processor;

import java.util.Optional;

@Path("/callback")
public class CallbackEndpoint {

    @Inject
    Processor processor;

    @POST
    @Path("{callbackId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void callback(String callbackId) throws Exception {

        Log.infof("callback received with id: %s", callbackId);
        // decide if callback successful or not
        boolean success = true;
        Optional<Job> job = Job.getJobWithCallbackId(callbackId);

        if (job.isEmpty()) {
            throw new RuntimeException("Got callback for a process that I couldn't find");
        }

        Job realJob = job.get();
        ProcessStateWithCallback processState = realJob.toProcessStateWithCallback();

        if (success) {
            Optional<ProcessState> next = processState.callbackNextState("rawJson");
            if (next.isPresent()) {
                processor.enqueueProcessState(next.get(), realJob.processId);
            }
        } else {
            processor.retryJob(realJob);
        }
    }
}
