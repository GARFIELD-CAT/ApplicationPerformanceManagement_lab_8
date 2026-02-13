package ru.dayagunov.lab_8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dayagunov.lab_8.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
