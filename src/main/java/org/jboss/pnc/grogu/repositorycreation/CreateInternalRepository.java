package org.jboss.pnc.grogu.repositorycreation;

import io.quarkus.logging.Log;
import org.jboss.pnc.api.repour.dto.RepourCreateRepositoryRequest;
import org.jboss.pnc.grogu.repositorycreation.dto.RepositoryConfiguration;
import org.jboss.pnc.grogu.repositorycreation.dto.RepositoryCreationRequest;
import org.jboss.pnc.grogu.util.GitUrlParser;
import org.jboss.pnc.grogu.util.ProcessState;

import java.util.Collections;
import java.util.Optional;

public class CreateInternalRepository implements ProcessState {

    String processId;
    RepositoryCreationRequest data;

    String accessToken;

    public CreateInternalRepository(String processId, RepositoryCreationRequest data, String accessToken) {
        this.processId = processId;
        this.data = data;
        this.accessToken = accessToken;
    }

    @Override
    public Optional<ProcessState> processAndNextState() {
        Log.infov("[{0}] Process: CloneRepository", processId);

        RepositoryConfiguration config = data.getTaskData().getRepositoryConfiguration();
        String repourUrl = data.getRepourBaseUrl();

        RepourCreateRepositoryRequest request = RepourCreateRepositoryRequest.builder()
                .project(getProjectName(config.getExternalUrl()))
                .ownerGroups(Collections.singletonList("ldap/jboss-prod"))
                .parentProject("jboss-prod-permissions")
                .build();

        // do rest request and wait for reply
        // if fail, return same process?
        return Optional.empty();
    }

    @Override
    public Optional<ProcessState> onCancel() {
        return Optional.empty();
    }

    private static String getProjectName(String externalUrl) {
        return GitUrlParser.generateInternalGitRepoName(externalUrl);
    }
}
