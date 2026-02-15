package ru.dayagunov.lab_8.adapters;

import org.springframework.stereotype.Component;
import ru.dayagunov.lab_8.model.Task;
import ru.dayagunov.lab_8.model.TaskDocument;
import ru.dayagunov.lab_8.ports.TaskRepositoryPort;
import ru.dayagunov.lab_8.repository.TaskDocumentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MongoTaskRepositoryAdapter implements TaskRepositoryPort {
    private final TaskDocumentRepository mongoRepository;

    public MongoTaskRepositoryAdapter(TaskDocumentRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public List<Task> findAll() {
        List<TaskDocument> documents = mongoRepository.findAll();
        List<Task> tasks = new ArrayList<>();

        for (var doc : documents) {
            tasks.add(convertDocumentToTask(doc));
        }

        return tasks;
    }

    @Override
    public Task save(Task task){
        TaskDocument document = convertTaskToDocument(task);
        mongoRepository.save(document);

        return task;
    }

    @Override
    public Optional<Task> findById(Integer id) {
        Optional<TaskDocument> documentTask = mongoRepository.findByTaskId(id);

        if (documentTask.isPresent()){
            return Optional.of(
                convertDocumentToTask(documentTask.get())
            );
        }

        return Optional.empty();
    }

    private Task convertDocumentToTask(TaskDocument document){
        return new Task(
                document.getTaskId(),
                document.getDescription(),
                document.getCompleted(),
                document.getAmount()
        );
    }

    private TaskDocument convertTaskToDocument(Task task){
        TaskDocument taskDocument = new TaskDocument();
        taskDocument.setTaskId(task.getId());
        taskDocument.setDescription(task.getDescription());
        taskDocument.setCompleted(task.getCompleted());
        taskDocument.setAmount(task.getAmount());

        return taskDocument;
    }
}
