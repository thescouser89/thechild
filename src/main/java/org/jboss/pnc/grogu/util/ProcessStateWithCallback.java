package org.jboss.pnc.grogu.util;

import java.util.Optional;
import java.util.UUID;

public interface ProcessStateWithCallback extends ProcessState {

    default String getCallbackId() {
        return UUID.randomUUID().toString();
    }

    Optional<ProcessState> callbackNextState(String rawJson);
}
