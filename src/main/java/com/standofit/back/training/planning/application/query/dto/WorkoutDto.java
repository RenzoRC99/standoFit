package com.standofit.back.training.planning.application.query.dto;

import java.util.List;
import java.util.UUID;

public class WorkoutDto {
    private final UUID id;
    private final String name;
    private final String description;
    private final List<WorkoutDayDto> days;
    private final String createdAt;
    private final String updatedAt;

    public WorkoutDto(UUID id, String name, String description, List<WorkoutDayDto> days,
                      String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.days = days;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<WorkoutDayDto> getDays() {
        return days;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}