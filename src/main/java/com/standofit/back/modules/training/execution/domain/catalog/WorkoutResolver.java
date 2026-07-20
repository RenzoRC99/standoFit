package com.standofit.back.modules.training.execution.domain.catalog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Port for resolving workout plan data. Decouples execution module from planning module. */
public interface WorkoutResolver {

  Optional<WorkoutInfo> resolveByDayId(UUID dayId);

  record WorkoutInfo(String workoutName, String dayName, List<PlannedExercise> plannedExercises) {}

  record PlannedExercise(UUID exerciseId, int sets, int reps, int restSeconds) {}
}
