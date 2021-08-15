package ru.itintego.javatest.repositories;

import ru.itintego.javatest.models.Role;

public interface RoleRepository extends SuperRepository<Role> {
    Role findByName(String role);
}