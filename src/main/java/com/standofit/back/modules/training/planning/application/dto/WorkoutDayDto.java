package com.standofit.back.modules.training.planning.application.dto;

import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object for WorkoutDay entity.
 *
 * @author standofit
 * @version 1.0
 */
public record WorkoutDayDto(UUID id, String name, List<WorkoutExerciseDto> exercises) {}
