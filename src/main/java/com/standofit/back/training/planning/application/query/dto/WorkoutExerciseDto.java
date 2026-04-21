package com.standofit.back.training.planning.application.query.dto;

import java.util.UUID;

public class WorkoutExerciseDto {
    private final UUID id;
    private final UUID exerciseId;
    private final int sets;
    private final int reps;
    private final int restSeconds;

    public WorkoutExerciseDto(UUID id, UUID exerciseId, int sets, int reps, int restSeconds) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.sets = sets;
        this.reps = reps;
        this.restSeconds = restSeconds;
    }

    public UUID getId() {
        return id;
    }

    public UUID getExerciseId() {
        return exerciseId;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public int getRestSeconds() {
        return restSeconds;
    }
}