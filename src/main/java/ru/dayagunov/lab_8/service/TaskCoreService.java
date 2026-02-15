package ru.dayagunov.lab_8.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.dayagunov.lab_8.model.CreateTaskDto;
import ru.dayagunov.lab_8.model.Task;
import ru.dayagunov.lab_8.ports.TaskRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskCoreService {
    private final TaskRepositoryPort taskRepositoryPort;
    AtomicInteger objectCounts = new AtomicInteger(0);

    @PersistenceContext
    private EntityManager entityManager;

    Random random = new Random();

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchValue;

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

    @Transactional
    public void createBatchTask(int count) {
        for (int i = 1; i <= count; i++) {
            Integer taskNum = objectCounts.getAndIncrement();
            String serviceName = taskRepositoryPort.toString();
            Task task;

            if (serviceName.contains("MongoTaskRepositoryAdapter")) {
                task = new Task(
                        taskNum,
                        "description",
                        false,
                        random.nextInt(1000 - 100) + 100
                );
            } else {
                task = new Task(
                        "description",
                        false,
                        random.nextInt(1000 - 100) + 100
                );
            }

            taskRepositoryPort.save(task);
            if (i % batchValue == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

//    @Cacheable("allTasks")
    public List<Task> getAllTasks() {
        return taskRepositoryPort.findAll();
    }

    public Task createTask(CreateTaskDto newTaskData) {
        Integer taskNum = objectCounts.getAndIncrement();
        String serviceName = taskRepositoryPort.toString();
        Task newTask;

        if (serviceName.contains("MongoTaskRepositoryAdapter")) {
            newTask = new Task(
                    taskNum,
                    "description",
                    false,
                    random.nextInt(1000 - 100) + 100
            );
        } else {
            newTask = new Task(
                    "description",
                    false,
                    random.nextInt(1000 - 100) + 100
            );
        }
        newTask.setDescription(newTaskData.getDescription());
        newTask.setCompleted(newTaskData.getCompleted());
        newTask.setAmount(newTaskData.getAmount());

        taskRepositoryPort.save(newTask);

        return newTask;
    }

//    @Cacheable("task")
    public Task getTaskById(Integer taskId) {
        Optional<Task> task = taskRepositoryPort.findById(taskId);

        return task.orElse(null);
    }

//    @CacheEvict(value = "task", allEntries = true)
    public Task updateTask(Integer taskId, CreateTaskDto newTaskData) {
        Optional<Task> OptTask = taskRepositoryPort.findById(taskId);

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

        return taskRepositoryPort.save(task);
    }
}
