package com.standofit.back.modules.training.execution.infrastructure.catalog;

import com.standofit.back.modules.training.execution.domain.catalog.WorkoutResolver;
import com.standofit.back.modules.training.planning.infrastructure.entity.WorkoutDayJpaEntity;
import com.standofit.back.modules.training.planning.infrastructure.entity.WorkoutJpaEntity;
import com.standofit.back.modules.training.planning.infrastructure.repository.WorkoutJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class WorkoutResolverImpl implements WorkoutResolver {

  private final WorkoutJpaRepository workoutJpaRepository;

  public WorkoutResolverImpl(WorkoutJpaRepository workoutJpaRepository) {
    this.workoutJpaRepository = workoutJpaRepository;
  }

  @Override
  public Optional<WorkoutInfo> resolveByDayId(UUID dayId) {
    Optional<WorkoutJpaEntity> workoutOpt = workoutJpaRepository.findByDayId(dayId);
    if (workoutOpt.isEmpty()) {
      return Optional.empty();
    }

    WorkoutJpaEntity workout = workoutOpt.get();
    Optional<WorkoutDayJpaEntity> dayOpt =
        workout.getDays().stream().filter(d -> d.getId().equals(dayId)).findFirst();

    if (dayOpt.isEmpty()) {
      return Optional.empty();
    }

    WorkoutDayJpaEntity day = dayOpt.get();

    List<PlannedExercise> plannedExercises =
        day.getExercises().stream()
            .map(
                we ->
                    new PlannedExercise(
                        we.getExerciseId(), we.getSets(), we.getReps(), we.getRestSeconds()))
            .toList();

    return Optional.of(new WorkoutInfo(workout.getName(), day.getName(), plannedExercises));
  }
}
