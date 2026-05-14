package com.standofit.back.modules.training.planning.application.command.archive_workout;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class ArchiveWorkoutService extends PlanningUseCase {

  public ArchiveWorkoutService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void archive(ArchiveWorkoutCommand command) {
    try {
      Workout workout = repository.getById(command.workoutId());
      repository.save(workout);
      publishEvent(
          PlanningActivityEvent.success(
              PlanningActivityType.WORKOUT_ARCHIVED,
              command.workoutId().toString(),
              PlanningActivityType.WORKOUT_ARCHIVED.getDefaultDescription()));
    } catch (Exception e  ) {
      publishEvent(
          PlanningActivityEvent.failure(
              PlanningActivityType.WORKOUT_ARCHIVED,
              command.workoutId().toString(),
              PlanningActivityType.WORKOUT_ARCHIVED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
