package org.jboss.pnc.grogu.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.jboss.pnc.grogu.util.ProcessState;
import org.jboss.pnc.grogu.util.ProcessStateWithCallback;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.jboss.pnc.grogu.util.ObjectMapperProvider.OBJECT_MAPPER;

/**
 * The Job model is used to mostly store the {@link org.jboss.pnc.grogu.util.ProcessState} into the database.
 * <p>
 * It can be converted from ProcessState to Job via the static {@link #newJobFromProcessState} method. It can be
 * converted from Job to ProcessState via the static {@link #toProcessState} method.
 */
@Entity
public class Job extends PanacheEntityBase {

    /**
     * See: https://stackoverflow.com/a/73635372/2907906 Needs the columnDefinition, otherwise I get weird locking error
     */
    @Id
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    public UUID id;

    /**
     * The process Id links all the different states for a process together
     */
    @Column(nullable = false)
    public String processId;

    /**
     * Let's keep it at 255 chars? Are we crazy enough to have class name longer than that?
     */
    @Column(nullable = false)
    public String stateClass;

    /**
     * We'll store the state object state in there as JSON. We want to make sure it's big enough
     */
    @Lob
    @Column(nullable = false)
    public String stateDataJson;

    /**
     * Number of retries for that state
     */
    public int retries;

    @CreationTimestamp
    @Column(updatable = false)
    public LocalDateTime created;

    @UpdateTimestamp
    public LocalDateTime updated;

    public ProcessState toProcessState() throws Exception {
        Class<ProcessState> c1 = (Class<ProcessState>) Class.forName(stateClass);
        return (ProcessState) OBJECT_MAPPER.readValue(stateDataJson, c1);
    }

    public ProcessStateWithCallback toProcessStateWithCallback() throws Exception {

        // can this class be a subtype of ProcessStateWithCallback ?
        if (Class.forName(stateClass).isAssignableFrom(ProcessStateWithCallback.class)) {
            Class<ProcessStateWithCallback> c1 = (Class<ProcessStateWithCallback>) Class.forName(stateClass);
            return (ProcessStateWithCallback) OBJECT_MAPPER.readValue(stateDataJson, c1);
        } else {
            throw new Exception("Hell no you can't assign " + stateClass + " to ProcessStateWithCallback");
        }
    }

    /**
     * Convert a ProcessState into a Job that we can then store in the database
     *
     * @param processId process id of that state
     * @param processState state to save into the database
     *
     * @return the Job object that can be stored into the database
     *
     * @throws Exception exception!
     */
    public static Job newJobFromProcessState(String processId, ProcessState processState) throws Exception {
        Job job = new Job();
        job.processId = processId;
        job.stateClass = processState.getClass().getName();
        job.stateDataJson = OBJECT_MAPPER.writeValueAsString(processState);

        return job;
    }

    /**
     * Return the first job for a processId. It contains all the initial data that the request sent
     *
     * @param processId the process id to identify
     * @return the first job
     */
    public static Optional<Job> getInitJob(String processId) {
        return find("from Job where processId = ?1", Sort.by("created").descending(), processId).firstResultOptional();
    }

    public static Optional<Job> getLatestJob(String processId) {
        return find("from Job where processId = ?1", Sort.by("created").descending(), processId).firstResultOptional();
    }

    public static Optional<Job> getJobWithUUID(String uuidString) {
        return findByIdOptional(UUID.fromString(uuidString));
    }

    /**
     * Get all the jobs for processId, ordered by the creation date ascending.
     *
     * @param processId process id of job
     *
     * @return list of jobs
     */
    public List<Job> getJobsWithProcessId(String processId) {
        return list("from Job where processId = ?!", Sort.by("created").ascending(), processId);
    }
}