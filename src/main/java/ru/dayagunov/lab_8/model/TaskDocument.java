package ru.dayagunov.lab_8.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private Integer taskId;
    private String description;
    private Boolean completed;
    private Integer amount;
}
