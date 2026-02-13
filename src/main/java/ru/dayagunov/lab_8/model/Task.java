package ru.dayagunov.lab_8.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String description;
    @Column
    private Boolean completed = false;
    @Column
    private Integer amount;

    public Task(String description, Boolean completed, Integer amount) {
        this.description = description;
        this.completed = completed;
        this.amount = amount;
    }

    public Task(String description, Integer amount) {
        this.description = description;
        this.amount = amount;
    }
}