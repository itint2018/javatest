package ru.itintego.javatest.repositories;

import ru.itintego.javatest.models.User;

import java.util.Optional;

public interface UserRepository extends SuperRepository<User> {
    Optional<User> findByLogin(String login);

    Optional<User> findByLoginAndPassword(String login, String password);

    Optional<User> findBySession(String session);
}