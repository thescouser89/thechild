package org.jboss.pnc.grogu.repositorycreation;

import io.quarkus.logging.Log;
import org.jboss.pnc.api.repour.dto.RepourCreateRepositoryRequest;
import org.jboss.pnc.grogu.repositorycreation.dto.RepositoryConfiguration;
import org.jboss.pnc.grogu.repositorycreation.dto.RepositoryCreationRequest;
import org.jboss.pnc.grogu.util.GitUrlParser;
import org.jboss.pnc.grogu.util.ProcessState;

import java.util.Collections;
import java.util.Optional;

public class CloneRepository implements ProcessState {
    String processId;
    RepositoryCreationRequest data;

    String accessToken;

    public CloneRepository(String processId, RepositoryCreationRequest data, String accessToken) {
        this.processId = processId;
        this.data = data;
        this.accessToken = accessToken;
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
