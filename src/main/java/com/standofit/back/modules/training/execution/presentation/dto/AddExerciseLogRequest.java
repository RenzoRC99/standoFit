package com.standofit.back.modules.training.execution.presentation.dto;

public record AddExerciseLogRequest(
        String logId,
        String exerciseId,
        int sets,
        int reps,
        int weight
) {
}