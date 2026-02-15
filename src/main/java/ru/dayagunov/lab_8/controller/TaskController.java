package ru.dayagunov.lab_8.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dayagunov.lab_8.configuration.AdapterConfig;
import ru.dayagunov.lab_8.model.Task;
import ru.dayagunov.lab_8.model.CreateTaskDto;
import ru.dayagunov.lab_8.ports.TaskRepositoryPort;
import ru.dayagunov.lab_8.service.TaskCoreService;
import ru.dayagunov.lab_8.service.TaskService;

import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskCoreService taskCoreService;

    public TaskController(
            TaskService taskService,
//            @Qualifier("JpaTaskCoreService") TaskCoreService taskCoreService)
//            @Qualifier("InMemoryTaskCoreService") TaskCoreService taskCoreService)
            @Qualifier("MongoTaskCoreService") TaskCoreService taskCoreService)
    {
        this.taskCoreService = taskCoreService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return ResponseEntity.ok(taskCoreService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id) {
        Task task = taskCoreService.getTaskById(id);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskDto newTaskData) {
        Task task = taskCoreService.createTask(newTaskData);

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id, @RequestBody CreateTaskDto newTaskData) {
        Task task = taskCoreService.updateTask(id, newTaskData);

        return ResponseEntity.ok(task);
    }

    @GetMapping("/avg-amount")
    public ResponseEntity<Double> getAvgAmount() {
        return ResponseEntity.ok(taskCoreService.calculateAverageTaskAmount());
    }

    @GetMapping("import/batch")
    public ResponseEntity<String> createBatchTask(@RequestParam(defaultValue = "1000") int count) {
        taskCoreService.createBatchTask(count);
        return ResponseEntity.ok("Imported " + count);
    }
}