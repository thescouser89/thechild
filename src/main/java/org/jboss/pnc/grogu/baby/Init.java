package org.jboss.pnc.grogu.baby;

import io.quarkus.logging.Log;
import org.jboss.pnc.grogu.util.ProcessState;

import java.util.Optional;

public class Init implements ProcessState {
    @Override
    public Optional<ProcessState> processAndNextState() throws Exception {
        Log.info("Init phase");
        return Optional.empty();
    }

    @Override
    public Optional<ProcessState> onCancel() {
        return Optional.empty();
    }
}
