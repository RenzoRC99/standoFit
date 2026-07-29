package com.standofit.back.modules.training.execution.domain.service;

import com.standofit.back.modules.training.execution.domain.SessionDomainErrors;
import com.standofit.back.modules.training.execution.domain.SessionDomainException;
import com.standofit.back.modules.training.execution.domain.catalog.ExerciseVerifier;
import com.standofit.back.modules.training.execution.domain.catalog.WorkoutDayVerifier;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import org.springframework.stereotype.Component;

@Component
public class SessionDomainValidator {

  private final WorkoutDayVerifier workoutDayVerifier;
  private final ExerciseVerifier exerciseVerifier;

  public SessionDomainValidator(
      WorkoutDayVerifier workoutDayVerifier, ExerciseVerifier exerciseVerifier) {
    this.workoutDayVerifier = workoutDayVerifier;
    this.exerciseVerifier = exerciseVerifier;
  }

  public void ensureDayExists(SessionDayId id) {
    if (!workoutDayVerifier.exists(id)) {
      throw new SessionDomainException(SessionDomainErrors.DAY_NOT_FOUND.getMessage());
    }
  }

  public void ensureExerciseExists(ExerciseId id) {
    if (!exerciseVerifier.exists(id)) {
      throw new SessionDomainException(SessionDomainErrors.EXERCISE_NOT_FOUND.getMessage());
    }
  }
}
