package org.jboss.pnc.grogu.util;

import java.util.Optional;

/**
 * Interface for the process finite state machine. It is used to process and decide the next state to move to
 */
public interface ProcessState {

    /**
     * Do whatever processing needs to be done for this state (like REST request) and decide the next state to move to
     *
     * @return Optional ProcessState: if empty, there are no next state to move to. If present, we move to that next state
     */
    Optional<ProcessState> processAndNextState();

    /**
     * Do whatever processing needs to be done for this or previous states on knowing we are cancelled
     *
     * @return Optional ProcessState: if empty, there are no next state to move to. If present, we move to that next state
     */
    Optional<ProcessState> onCancel();
}