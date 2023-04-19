package org.jboss.pnc.grogu.util;

public interface Processor {
    public void enqueue(ProcessState s);
    public ProcessState pop();
}
