package com.standofit.back.modules.training.planning.application.command.change_workout_description;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutDescription;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class ChangeWorkoutDescriptionService extends PlanningUseCase {

  public ChangeWorkoutDescriptionService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void changeDescription(ChangeWorkoutDescriptionCommand command) {
    try {
      Workout workout = repository.getById(command.workoutId());

      Workout updated = workout.changeDescription(new WorkoutDescription(command.description()));

      repository.save(updated);
      publishEvent(
          PlanningActivityEvent.success(
              PlanningActivityType.WORKOUT_DESCRIPTION_CHANGED,
              command.workoutId().toString(),
              PlanningActivityType.WORKOUT_DESCRIPTION_CHANGED.getDefaultDescription()));
    } catch (Exception e) {
      publishEvent(
          PlanningActivityEvent.failure(
              PlanningActivityType.WORKOUT_DESCRIPTION_CHANGED,
              command.workoutId().toString(),
              PlanningActivityType.WORKOUT_DESCRIPTION_CHANGED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}

