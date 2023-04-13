package org.jboss.pnc.grogu.repositorycreation;

import org.jboss.pnc.grogu.util.ProcessState;

import java.util.Optional;

public class Init implements ProcessState {

    String processId;
    String data;

    public Init(String processId, String data) {
        this.processId = processId;
        this.data = data;
    }

    @Override
    public Optional<ProcessState> processAndNextState() {
        // find if there is any entry, if yes, increment retries
        // store data and process Id
        // do any process
        // decide on next step and return nextState
        return Optional.empty();
    }

    @Override
    public Optional<ProcessState> onCancel() {
        return Optional.empty();
    }
}
