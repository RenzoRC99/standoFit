package com.standofit.back.training.planning.application.query.dto;

import java.util.List;
import java.util.UUID;

public class WorkoutDayDto {
    private final UUID id;
    private final String name;
    private final List<WorkoutExerciseDto> exercises;

    public WorkoutDayDto(UUID id, String name, List<WorkoutExerciseDto> exercises) {
        this.id = id;
        this.name = name;
        this.exercises = exercises;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<WorkoutExerciseDto> getExercises() {
        return exercises;
    }
}