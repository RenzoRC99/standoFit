package com.standofit.back.training.planning.presentation.dto;

import java.util.List;
import java.util.UUID;

public record CreateWorkoutRequest(
        String name,
        String description,
        List<DayRequest> days
) {
    public record DayRequest(String name, List<ExerciseRequest> exercises) {}

    public record ExerciseRequest(
            UUID exerciseId,
            int sets,
            int reps,
            int restSeconds
    ) {}
}