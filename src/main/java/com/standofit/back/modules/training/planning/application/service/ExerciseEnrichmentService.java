package com.standofit.back.modules.training.planning.application.service;

import com.standofit.back.modules.training.planning.domain.catalog.ExerciseCatalog;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Service for enriching workout data with exercise details from the exercises catalog. Uses
 * ExerciseCatalog interface to maintain module boundary.
 */
@Component
public class ExerciseEnrichmentService {

  private final ExerciseCatalog exerciseCatalog;

  public ExerciseEnrichmentService(ExerciseCatalog exerciseCatalog) {
    this.exerciseCatalog = exerciseCatalog;
  }

  /**
   * Loads exercise names and muscle groups for all exercises referenced in the given workouts.
   *
   * @param workouts collection of workouts to enrich
   * @return ExerciseMaps containing name and muscleGroup lookups by exerciseId
   */
  public ExerciseMaps loadExerciseData(Collection<Workout> workouts) {
    Set<UUID> exerciseIds =
        workouts.stream()
            .flatMap(w -> w.getDays().stream())
            .flatMap(d -> d.getExercises().stream())
            .map(ex -> ex.getExerciseId().value())
            .collect(Collectors.toSet());

    var data = exerciseCatalog.findByIds(exerciseIds);
    return new ExerciseMaps(data.names(), data.muscleGroups());
  }

  /**
   * Loads exercise names and muscle groups for a single workout.
   *
   * @param workout the workout to enrich
   * @return ExerciseMaps containing name and muscleGroup lookups by exerciseId
   */
  public ExerciseMaps loadExerciseData(Workout workout) {
    return loadExerciseData(java.util.List.of(workout));
  }

  /** Container for exercise enrichment data. */
  public record ExerciseMaps(
      java.util.Map<UUID, String> names, java.util.Map<UUID, String> muscleGroups) {}
}
