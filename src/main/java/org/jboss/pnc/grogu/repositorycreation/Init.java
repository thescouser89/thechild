package org.jboss.pnc.grogu.repositorycreation;

import org.jboss.pnc.grogu.repositorycreation.dto.RepositoryCreationRequest;
import org.jboss.pnc.grogu.util.ProcessState;

import java.util.Optional;

public class Init implements ProcessState {

    String processId;
    RepositoryCreationRequest data;

    /**
     * Use the access token from the sender
     */
    String accessToken;

    public Init(String processId, RepositoryCreationRequest request, String accessToken) {
        this.processId = processId;
        this.data = request;
        this.accessToken = accessToken;
    }

    @Override
    public Optional<ProcessState> processAndNextState() {
        // find if there is any entry, if yes, increment retries
        // store data and process Id
        // do any process
        // decide on next step and return nextState
        return Optional.of(new CreateInternalRepository(processId, data, accessToken));
    }

    @Override
    public Optional<ProcessState> onCancel() {
        return Optional.empty();
    }
}
