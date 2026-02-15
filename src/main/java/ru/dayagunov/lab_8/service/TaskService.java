package ru.dayagunov.lab_8.service;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.dayagunov.lab_8.model.CreateTaskDto;
import ru.dayagunov.lab_8.model.Task;
import ru.dayagunov.lab_8.repository.TaskRepository;
import org.springframework.cache.Cache;

import java.util.List;
import java.util.Optional;


@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final Cache taskCache;

    public TaskService(TaskRepository taskRepository, CacheManager cacheManager){
        this.taskRepository = taskRepository;
        Long count = this.taskRepository.count();

        if (count < 1000) {
            for (int i = 0; i < 100; i++) {
                this.taskRepository.save(new Task("Купить продукты", i));
                this.taskRepository.save(new Task("Купить продукты", i));
                this.taskRepository.save(new Task("Написать отчет по проекту", i));
            }
        }

        this.taskCache = cacheManager.getCache("allTasks");

        if (this.taskCache == null) {
            throw new IllegalArgumentException("Cache not found");
        }
    }

    @CacheEvict(value = "task", allEntries = true)
    public Task updateTask(Integer taskId, CreateTaskDto newTaskData) {
        Optional<Task> OptTask = taskRepository.findById(taskId);

        if (OptTask.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("Запись с id=%s не существует", taskId)
            );

        Task task = OptTask.get();

        if (newTaskData.getAmount() != null) {
            task.setAmount(newTaskData.getAmount());
        }

        if (newTaskData.getDescription() != null) {
            task.setDescription(newTaskData.getDescription());
        }

        if (newTaskData.getCompleted() != null) {
            task.setCompleted(newTaskData.getCompleted());
        }

        return taskRepository.save(task);
    }
}
