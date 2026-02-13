package ru.dayagunov.lab_8.adapters;

import org.springframework.stereotype.Component;
import ru.dayagunov.lab_8.model.Task;
import ru.dayagunov.lab_8.ports.TaskRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemoryTaskRepositoryAdapter implements TaskRepositoryPort {
    private final Map<Integer, Task> storage = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    public InMemoryTaskRepositoryAdapter() {
        Integer count = findAll().size();

        if (count < 1000) {
            for (int i = 0; i < 100; i++) {
                save(new Task("Сделать ДЗ 1", false, i*10));
                save(new Task("Сделать ДЗ 2", false, i*10));
                save(new Task("Сделать ДЗ 3", false, i*10));
            }
        }
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idGenerator.incrementAndGet());
        }
        storage.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return Optional.ofNullable(storage.get(id));
    }
}