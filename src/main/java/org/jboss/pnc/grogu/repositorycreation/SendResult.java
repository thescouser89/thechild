package org.jboss.pnc.grogu.repositorycreation;

import org.jboss.pnc.grogu.util.ProcessState;

import java.util.Optional;

public class SendResult implements ProcessState {
    @Override
    public Optional<ProcessState> processAndNextState() {
        return Optional.empty();
    }

    @Override
    public Optional<ProcessState> onCancel() {
        return Optional.empty();
    }
}
