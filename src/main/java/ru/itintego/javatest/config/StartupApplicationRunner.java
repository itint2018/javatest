package ru.itintego.javatest.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import ru.itintego.javatest.repositories.IconRepository;
import ru.itintego.javatest.repositories.UserRepository;

import javax.sql.DataSource;

@Component
public class StartupApplicationRunner {

    private final UserRepository userRepository;
    private final IconRepository iconRepository;
    private final DataSource dataSource;

    public StartupApplicationRunner(UserRepository userRepository, IconRepository iconRepository, DataSource dataSource) {
        this.userRepository = userRepository;
        this.iconRepository = iconRepository;
        this.dataSource = dataSource;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() throws Exception {
        if (userRepository.count() == 0) {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/data.sql"));
            resourceDatabasePopulator.execute(dataSource);
        }
        if (iconRepository.count() == 0) {
            ResourceDatabasePopulator resourceDatabasePopulator1 = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/icons.sql"));
            resourceDatabasePopulator1.execute(dataSource);
        }
    }
}
