package org.jboss.pnc.grogu.baby;

import io.quarkus.logging.Log;
import org.jboss.pnc.grogu.util.ProcessState;

import java.util.Optional;
import java.util.Random;

public class Next implements ProcessState {
    @Override
    public Optional<ProcessState> processAndNextState() throws Exception {
        Log.info("Next phase");
        Random random = new Random();
        if (random.nextInt() % 5 != 0) {
            throw new RuntimeException();
        }

        return Optional.empty();
    }

    @Override
    public Optional<ProcessState> onCancel() {
        return Optional.empty();
    }
}
