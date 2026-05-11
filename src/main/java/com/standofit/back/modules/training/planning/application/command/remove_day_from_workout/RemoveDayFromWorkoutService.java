package com.standofit.back.modules.training.planning.application.command.remove_day_from_workout;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RemoveDayFromWorkoutService extends PlanningUseCase {

  public RemoveDayFromWorkoutService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void removeDay(RemoveDayFromWorkoutCommand command) {
    try {
      Workout workout =
          repository
              .findById(command.workoutId())
              .orElseThrow(
                  () -> new IllegalArgumentException("Workout not found: " + command.workoutId()));

      Workout updated = workout.removeDays(List.of(new WorkoutDayId(command.dayId())));

      repository.save(updated);
      publishEvent(
          PlanningActivityEvent.success(
              PlanningActivityType.WORKOUT_DAY_REMOVED,
              command.workoutId().toString(),
              PlanningActivityType.WORKOUT_DAY_REMOVED.getDefaultDescription()));
    } catch (Exception e) {
      publishEvent(
          PlanningActivityEvent.failure(
              PlanningActivityType.WORKOUT_DAY_REMOVED,
              command.workoutId().toString(),
              PlanningActivityType.WORKOUT_DAY_REMOVED.getDefaultDescription(),
              e.getMessage()));
      throw e;
    }
  }
}
