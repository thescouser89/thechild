package org.jboss.pnc.grogu.baby;

import org.jboss.pnc.grogu.util.ProcessState;

import java.util.Optional;

public class Next implements ProcessState {
    @Override
    public Optional<ProcessState> processAndNextState() throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<ProcessState> onCancel() {
        return Optional.empty();
    }
}
