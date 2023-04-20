package org.jboss.pnc.grogu.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class CancelledProcess extends PanacheEntityBase {

    @Id
    public String processId;
    public String data;

    @CreationTimestamp
    public LocalDateTime created;

    public static boolean isCancelled(String processId) {
        return findByIdOptional(processId).isPresent();
    }
}
