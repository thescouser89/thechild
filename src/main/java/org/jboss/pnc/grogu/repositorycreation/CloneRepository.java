package org.jboss.pnc.grogu.repositorycreation;

import org.jboss.pnc.grogu.repositorycreation.dto.RepositoryCreationRequest;
import org.jboss.pnc.grogu.repositorycreation.dto.RepourInternalScmResponse;
import org.jboss.pnc.grogu.util.ProcessState;

import java.util.Map;
import java.util.Optional;

public class CloneRepository implements ProcessState {
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
        return Optional.empty();
    }

    @Override
    public Optional<ProcessState> onCancel() {
        return Optional.empty();
    }
}
