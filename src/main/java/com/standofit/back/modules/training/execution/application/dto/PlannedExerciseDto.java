package com.standofit.back.modules.training.execution.application.dto;

import java.util.UUID;

public record PlannedExerciseDto(
    UUID exerciseId,
    String exerciseName,
    String muscleGroup,
    Integer sets,
    Integer reps,
    Integer restSeconds) {}
