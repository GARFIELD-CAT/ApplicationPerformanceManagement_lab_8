package ru.dayagunov.lab_8.service;

import ru.dayagunov.lab_8.model.Task;
import ru.dayagunov.lab_8.ports.TaskRepositoryPort;

import java.util.List;

public class TaskCoreService {
    private final TaskRepositoryPort taskRepositoryPort;

    public TaskCoreService(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

    public Double calculateAverageTaskAmount() {
        List<Task> tasks = taskRepositoryPort.findAll();

        if (tasks == null || tasks.isEmpty()) {
            return 0.0;
        }

        return tasks.parallelStream()
                .mapToInt(Task::getAmount)
                .average()
                .orElse(0.0);
    }
}
