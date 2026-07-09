package com.standofit.back.modules.training.planning.application.dto;

import java.util.UUID;

/**
 * Data Transfer Object for WorkoutExercise entity.
 *
 * @author standofit
 * @version 1.0
 */
public record WorkoutExerciseDto(
    UUID id,
    UUID exerciseId,
    String exerciseName,
    String muscleGroup,
    int sets,
    int reps,
    int restSeconds) {}
