package org.jboss.pnc.grogu.queue;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.pnc.grogu.util.Executor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class KafkaExecutor {

    @Inject
    Executor executor;

    @Incoming("price-create")
    @Transactional
    public void execute(UUID uuid) throws Exception {
        Log.info("Calling from execute");
        executor.execute(uuid);
    }
}
