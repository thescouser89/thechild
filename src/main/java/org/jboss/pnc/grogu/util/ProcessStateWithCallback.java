package org.jboss.pnc.grogu.util;

import java.util.Optional;

public interface ProcessStateWithCallback extends ProcessState {

    String getCallbackUrl();

    Optional<ProcessState> callbackNextState(String rawJson);
}
