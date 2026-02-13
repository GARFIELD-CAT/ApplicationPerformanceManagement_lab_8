package ru.dayagunov.lab_8.adapters;

import org.springframework.stereotype.Component;
import ru.dayagunov.lab_8.model.Task;
import ru.dayagunov.lab_8.ports.TaskRepositoryPort;
import ru.dayagunov.lab_8.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Component
public class JpaTaskRepositoryAdapter implements TaskRepositoryPort {
    private final TaskRepository jpaRepository;

    public JpaTaskRepositoryAdapter(TaskRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Task> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Task save(Task task){
        return jpaRepository.save(task);
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return jpaRepository.findById(id);
    }
}