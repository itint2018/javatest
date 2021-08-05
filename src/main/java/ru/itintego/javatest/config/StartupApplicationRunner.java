package ru.itintego.javatest.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import ru.itintego.javatest.models.Role;
import ru.itintego.javatest.repositories.RoleRepository;

public class StartupApplicationRunner implements ApplicationRunner {
    private final RoleRepository roleRepository;

    public StartupApplicationRunner(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
