package ru.itintego.javatest.jpa_events;

import ru.itintego.javatest.models.SuperEntity;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public interface EventListener {
    @PrePersist
    void prePersist(SuperEntity superEntity);

    @PostPersist
    void postPersist(SuperEntity superEntity);

    @PreUpdate
    void preUpdate(SuperEntity superEntity);

    @PostUpdate
    void postUpdate(SuperEntity superEntity);
}