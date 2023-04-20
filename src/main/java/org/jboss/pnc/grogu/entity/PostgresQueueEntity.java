package org.jboss.pnc.grogu.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class PostgresQueueEntity extends PanacheEntityBase {
    @Id
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    public UUID id;

    /**
     * The process Id links all the different states for a process together
     */
    @Column(nullable = false)
    public UUID payload;

    @CreationTimestamp
    @Column(updatable = false)
    public LocalDateTime created;
}
