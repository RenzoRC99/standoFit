package com.standofit.back.modules.training.execution.application.dto;

import java.util.UUID;

/**
 * Data Transfer Object for ExerciseLog entity.
 */
public record ExerciseLogDto(
    UUID id,
    String name,
    Integer sets,
    Integer reps,
    Double weight) {}