package ru.dayagunov.lab_8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dayagunov.lab_8.model.TaskDocument;

import java.util.Optional;

public interface TaskDocumentRepository extends MongoRepository<TaskDocument, String> {
    Optional<TaskDocument> findByTaskId(Integer taskId);
}