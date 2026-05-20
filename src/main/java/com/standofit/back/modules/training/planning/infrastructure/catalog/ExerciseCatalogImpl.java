package com.standofit.back.modules.training.planning.infrastructure.catalog;

import com.standofit.back.modules.exercises.Exercise;
import com.standofit.back.modules.exercises.repository.ExerciseRepository;
import com.standofit.back.modules.training.planning.domain.catalog.ExerciseCatalog;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Implementation of ExerciseCatalog that bridges to the exercises module.
 * This is the only place in planning module that directly references exercises module.
 */
@Component
public class ExerciseCatalogImpl implements ExerciseCatalog {

  private final ExerciseRepository exerciseRepository;

  public ExerciseCatalogImpl(ExerciseRepository exerciseRepository) {
    this.exerciseRepository = exerciseRepository;
  }

  @Override
  public ExerciseData findByIds(Set<UUID> ids) {
    if (ids.isEmpty()) {
      return new ExerciseData(Map.of(), Map.of());
    }

    Set<String> stringIds = ids.stream().map(UUID::toString).collect(Collectors.toSet());
    List<Exercise> exercises = exerciseRepository.findAllById(stringIds);

    Map<UUID, String> names = exercises.stream()
        .collect(Collectors.toMap(
            ex -> UUID.fromString(ex.getId()),
            Exercise::getName
        ));

    Map<UUID, String> muscleGroups = exercises.stream()
        .collect(Collectors.toMap(
            ex -> UUID.fromString(ex.getId()),
            ex -> ex.getMuscleGroup().name()
        ));

    return new ExerciseData(names, muscleGroups);
  }

  @Override
  public Optional<ExerciseInfo> findById(UUID id) {
    return exerciseRepository.findById(id.toString()).map(this::toExerciseInfo);
  }

  private ExerciseInfo toExerciseInfo(Exercise exercise) {
    return new ExerciseInfo(
        UUID.fromString(exercise.getId()),
        exercise.getName(),
        exercise.getMuscleGroup().name()
    );
  }
}
