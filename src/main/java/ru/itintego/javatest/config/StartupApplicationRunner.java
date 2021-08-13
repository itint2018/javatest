package ru.itintego.javatest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;

@Component
public class StartupApplicationRunner {

    @Autowired
    private DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void run() throws Exception {
        File file = new File(System.getProperty("user.dir") + ".firstStart");
        if (!file.exists()) {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/data.sql"));
            ResourceDatabasePopulator resourceDatabasePopulator1 = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/icons.sql"));
            resourceDatabasePopulator.execute(dataSource);
            resourceDatabasePopulator1.execute(dataSource);
            file.createNewFile();
        }
    }
}
