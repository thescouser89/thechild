package org.jboss.pnc.grogu.repositorycreation;

import org.jboss.pnc.grogu.repositorycreation.dto.RepositoryCreationRequest;
import org.jboss.pnc.grogu.util.ProcessState;

import java.util.Map;
import java.util.Optional;

public class Init implements ProcessState {

    String processId;
    RepositoryCreationRequest data;
    Map<String, String> headers;

    public Init(String processId, RepositoryCreationRequest request, Map<String, String> headers) {
        this.processId = processId;
        this.data = request;
        this.headers = headers;
    }

    @Override
    public Optional<ProcessState> processAndNextState() {
        // find if there is any entry, if yes, increment retries
        // store data and process Id
        // do any process
        // decide on next step and return nextState
        return Optional.of(new CreateInternalRepository(processId, data, headers));
    }

    @Override
    public Optional<ProcessState> onCancel() {
        return Optional.empty();
    }
}
