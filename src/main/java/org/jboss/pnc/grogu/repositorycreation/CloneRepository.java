package org.jboss.pnc.grogu.repositorycreation;

import org.jboss.pnc.grogu.repositorycreation.dto.RepositoryCreationRequest;
import org.jboss.pnc.grogu.repositorycreation.dto.RepourInternalScmResponse;
import org.jboss.pnc.grogu.util.ProcessState;
import org.jboss.pnc.grogu.util.ProcessStateWithCallback;

import java.util.Map;
import java.util.Optional;

/**
 * TODO: uses callback
 */
public class CloneRepository implements ProcessStateWithCallback {
    String processId;
    RepositoryCreationRequest data;
    RepourInternalScmResponse repourResponse;

    Map<String, String> headers;

    public CloneRepository(
            String processId,
            RepositoryCreationRequest data,
            RepourInternalScmResponse repourResponse,
            Map<String, String> headers) {
        this.processId = processId;
        this.data = data;
        this.repourResponse = repourResponse;
        this.headers = headers;
    }

    @Override
    public Optional<ProcessState> processAndNextState() {
        // TODO: send request, then return Optional.empty()
        // TODO: we could receive the callback before this is saved. Is that an issue?
        return Optional.empty();
    }

    @Override
    public Optional<ProcessState> onCancel() {
        return Optional.empty();
    }

    @Override
    public String getCallbackId() {
        return null;
    }

    @Override
    public Optional<ProcessState> callbackNextState(String rawJson) {
        return Optional.empty();
    }
}
