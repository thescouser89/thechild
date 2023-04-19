package org.jboss.pnc.grogu.repositorycreation;

import io.quarkus.logging.Log;
import org.jboss.pnc.api.repour.dto.RepourCreateRepositoryRequest;
import org.jboss.pnc.grogu.repositorycreation.dto.RepositoryConfiguration;
import org.jboss.pnc.grogu.repositorycreation.dto.RepositoryCreationRequest;
import org.jboss.pnc.grogu.repositorycreation.dto.RepourInternalScmResponse;
import org.jboss.pnc.grogu.util.GitUrlParser;
import org.jboss.pnc.grogu.util.ProcessState;
import org.jboss.pnc.grogu.util.RestClient;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.jboss.pnc.grogu.util.ObjectMapperProvider.OBJECT_MAPPER;

public class CreateInternalRepository implements ProcessState {

    String processId;
    RepositoryCreationRequest data;

    Map<String, String> headers;

    public CreateInternalRepository(String processId, RepositoryCreationRequest data, Map<String, String> headers) {
        this.processId = processId;
        this.data = data;
        this.headers = headers;
    }

    @Override
    public Optional<ProcessState> processAndNextState() throws Exception {
        Log.infov("[{0}] Process: CloneRepository", processId);

        RepositoryConfiguration config = data.getTaskData().getRepositoryConfiguration();
        String repourUrl = data.getRepourBaseUrl();

        RepourCreateRepositoryRequest request = RepourCreateRepositoryRequest.builder()
                .project(getProjectName(config.getExternalUrl()))
                .ownerGroups(Collections.singletonList("ldap/jboss-prod"))
                .parentProject("jboss-prod-permissions")
                .build();

        // TODO: handle failure!
        String responseJson = RestClient
                .post(repourUrl + "/internal-scm", OBJECT_MAPPER.writeValueAsString(request), headers);
        RepourInternalScmResponse response = OBJECT_MAPPER.readValue(responseJson, RepourInternalScmResponse.class);

        return Optional.of(new CloneRepository(processId, data, response, headers));
    }

    @Override
    public Optional<ProcessState> onCancel() {
        return Optional.empty();
    }

    private static String getProjectName(String externalUrl) {
        return GitUrlParser.generateInternalGitRepoName(externalUrl);
    }
}
