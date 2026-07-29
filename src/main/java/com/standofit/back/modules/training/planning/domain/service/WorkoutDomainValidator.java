package com.standofit.back.modules.training.planning.domain.service;

import com.standofit.back.modules.training.planning.domain.WorkoutDomainErrors;
import com.standofit.back.modules.training.planning.domain.WorkoutDomainException;
import com.standofit.back.modules.training.planning.domain.catalog.ExerciseCatalog;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class WorkoutDomainValidator {

  private final ExerciseCatalog exerciseCatalog;

  public WorkoutDomainValidator(ExerciseCatalog exerciseCatalog) {
    this.exerciseCatalog = exerciseCatalog;
  }

  public void ensureExercisesExist(List<ExerciseId> ids) {
    if (ids == null || ids.isEmpty()) {
      return;
    }

    Set<UUID> idValues = ids.stream().map(ExerciseId::value).collect(Collectors.toSet());
    var data = exerciseCatalog.findByIds(idValues);

    List<UUID> missing = idValues.stream().filter(id -> !data.names().containsKey(id)).toList();

    if (!missing.isEmpty()) {
      throw new WorkoutDomainException(
          WorkoutDomainErrors.EXERCISE_NOT_FOUND_IN_CATALOG.getMessage());
    }
  }
}
