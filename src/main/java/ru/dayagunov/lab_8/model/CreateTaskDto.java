package ru.dayagunov.lab_8.model;

import lombok.*;


@Getter
@Setter
public class CreateTaskDto {
    private String description;
    private Boolean completed;
    private Integer amount;
}
