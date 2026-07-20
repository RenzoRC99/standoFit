package com.standofit.back.modules.training.execution.application.dto;

import java.util.List;
import java.util.UUID;

/** Data Transfer Object for Session entity. */
public record SessionDto(
    UUID id,
    UUID dayId,
    String workoutName,
    String dayName,
    String status,
    List<ExerciseLogDto> exercises,
    List<PlannedExerciseDto> plannedExercises,
    String notes,
    Integer durationMinutes,
    Integer totalVolume,
    Integer totalExercises,
    Integer totalSets,
    String startedAt,
    String finishedAt) {}
