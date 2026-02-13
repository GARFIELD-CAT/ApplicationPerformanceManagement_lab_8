package ru.dayagunov.lab_8.ports;

import ru.dayagunov.lab_8.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepositoryPort {
    List<Task> findAll();
    Task save(Task task);
    Optional<Task> findById(Integer id);
}
