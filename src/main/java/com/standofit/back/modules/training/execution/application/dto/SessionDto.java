package com.standofit.back.modules.training.execution.application.dto;

import java.util.List;
import java.util.UUID;

/** Data Transfer Object for Session entity. */
public record SessionDto(
    UUID id,
    UUID dayId,
    String status,
    List<ExerciseLogDto> exercises,
    String notes,
    String startedAt,
    String finishedAt) {}
