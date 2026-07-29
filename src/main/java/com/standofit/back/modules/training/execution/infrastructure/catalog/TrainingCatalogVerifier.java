package com.standofit.back.modules.training.execution.infrastructure.catalog;

import com.standofit.back.modules.exercises.repository.ExerciseRepository;
import com.standofit.back.modules.training.execution.domain.catalog.ExerciseVerifier;
import com.standofit.back.modules.training.execution.domain.catalog.WorkoutDayVerifier;
import com.standofit.back.modules.training.planning.infrastructure.repository.WorkoutDayJpaRepository;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class TrainingCatalogVerifier implements WorkoutDayVerifier, ExerciseVerifier {

  private final WorkoutDayJpaRepository workoutDayJpaRepository;
  private final ExerciseRepository exerciseRepository;

  public TrainingCatalogVerifier(
      WorkoutDayJpaRepository workoutDayJpaRepository, ExerciseRepository exerciseRepository) {
    this.workoutDayJpaRepository = workoutDayJpaRepository;
    this.exerciseRepository = exerciseRepository;
  }

  @Override
  public boolean exists(SessionDayId id) {
    return workoutDayJpaRepository.existsById(id.value());
  }

  @Override
  public boolean exists(ExerciseId id) {
    return exerciseRepository.existsById(id.value().toString());
  }
}
