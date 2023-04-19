package org.jboss.pnc.grogu.util;

import javax.enterprise.context.ApplicationScoped;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@ApplicationScoped
public class SimpleQueueProcessor implements Processor {

    Queue<ProcessState> queue = new LinkedBlockingQueue<>();

    @Override
    public void enqueue(ProcessState s) {
        queue.add(s);
    }

    @Override
    public ProcessState pop() {
        return queue.remove();
    }
}
