package com.standofit.back.modules.training.execution.application.dto;

import java.util.UUID;

public record ExerciseLogDto(
    UUID id, UUID exerciseId, Integer sets, Integer reps, Integer weight) {}
