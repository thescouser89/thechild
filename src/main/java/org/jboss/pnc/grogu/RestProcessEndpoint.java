package org.jboss.pnc.grogu;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/start")
public class RestProcessEndpoint {

    @GET
    @Path("build")
    @Produces(MediaType.APPLICATION_JSON)
    public String build(String data) {
        return "yes";
    }

    @GET
    @Path("repository-creation")
    @Produces(MediaType.APPLICATION_JSON)
    public String repositoryCreation() {
        return "yes";
    }

    @GET
    @Path("milestone-release")
    @Produces(MediaType.APPLICATION_JSON)
    public String milestoneRelease() {
        return "yes";
    }

    @GET
    @Path("deliverables-analyzer")
    @Produces(MediaType.APPLICATION_JSON)
    public String deliverablesAnalyzer() {
        return "yes";
    }

    @GET
    @Path("build-rex")
    @Produces(MediaType.APPLICATION_JSON)
    public String buildRex() {
        return "yes";
    }
}
