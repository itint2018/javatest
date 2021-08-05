package ru.itintego.javatest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.itintego.javatest.models.SuperEntity;

@NoRepositoryBean
public interface SuperRepository<T extends SuperEntity> extends JpaRepository<T, Long> {
}
