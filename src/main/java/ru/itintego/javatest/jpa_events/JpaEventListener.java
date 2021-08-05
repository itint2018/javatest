package ru.itintego.javatest.jpa_events;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import ru.itintego.javatest.models.SuperEntity;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
public class JpaEventListener implements EventListener {
    private final Logger log;

    public JpaEventListener(Logger log) {
        this.log = log;
    }

    @Override
    @PrePersist
    public void prePersist(SuperEntity superEntity) {
        log.info("Try to persist {}", superEntity);
    }

    @Override
    @PostPersist
    public void postPersist(SuperEntity superEntity) {
        log.info("Persisted {}", superEntity);
    }

    @Override
    @PreUpdate
    public void preUpdate(SuperEntity superEntity) {
        log.info("Try to Update {}", superEntity);
    }

    @Override
    @PostUpdate
    public void postUpdate(SuperEntity superEntity) {
        log.info("Updated {}", superEntity);
    }
}
