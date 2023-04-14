package org.jboss.pnc.grogu;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.logging.Log;
import org.jboss.pnc.grogu.model.Job;

import java.util.Optional;

@Path("/callback")
public class CallbackEndpoint {

    @POST
    @Path("{callbackId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void callback(String callbackId) {
        Log.infof("callback received with id: %s", callbackId);
        // decide if callback successful or not
        boolean success = true;
        Optional<Job> job = Job.getLatestJob(callbackId);

        if (job.isEmpty()) {
            throw new RuntimeException("Got callback for a process that I couldn't find");
        }

        Job realJob = job.get();
        if (success) {
            // do something
        } else {
            // repeat the old process
        }

    }
}
