package ru.itintego.javatest.jpa_events;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itintego.javatest.models.SuperEntity;

import javax.persistence.*;

@Component
public class JpaEventListener implements EventListener {

    @Autowired
    private Logger log;


    public JpaEventListener() {

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
        superEntity.setClazz(superEntity.getClass().getDeclaredAnnotation(Table.class).name());
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
        superEntity.setClazz(superEntity.getClass().getDeclaredAnnotation(Table.class).name());
    }

    @Override
    @PostLoad
    public void postLoad(SuperEntity superEntity) {
        log.info("Updated {}", superEntity);
        superEntity.setClazz(superEntity.getClass().getDeclaredAnnotation(Table.class).name());
    }
}
