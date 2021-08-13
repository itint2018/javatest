package ru.itintego.javatest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itintego.javatest.models.Icon;

public interface IconRepository extends JpaRepository<Icon, String> {
}
