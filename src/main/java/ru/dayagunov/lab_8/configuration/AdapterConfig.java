package ru.dayagunov.lab_8.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dayagunov.lab_8.adapters.InMemoryTaskRepositoryAdapter;
import ru.dayagunov.lab_8.adapters.JpaTaskRepositoryAdapter;
import ru.dayagunov.lab_8.service.TaskCoreService;

@Configuration
public class AdapterConfig {

    @Bean
    @Qualifier("JpaTaskCoreService")
    public TaskCoreService jpaTaskCoreService(
            JpaTaskRepositoryAdapter repositoryPort
    ) {
        return new TaskCoreService(repositoryPort);
    }

    @Bean
    @Qualifier("InMemoryTaskCoreService")
    public TaskCoreService inMemoryTaskCoreService(
            InMemoryTaskRepositoryAdapter repositoryPort
    ) {
        return new TaskCoreService(repositoryPort);
    }

}
