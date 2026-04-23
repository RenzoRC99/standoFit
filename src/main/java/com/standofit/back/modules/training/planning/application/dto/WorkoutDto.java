package com.standofit.back.modules.training.planning.application.dto;

import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object for Workout entity.
 *
 * @author standofit
 * @version 1.0
 */
public record WorkoutDto(
        UUID id,
        String name,
        String description,
        List<WorkoutDayDto> days,
        String createdAt,
        String updatedAt
) {
}
