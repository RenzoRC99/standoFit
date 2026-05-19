package com.standofit.back.modules.training.planning.application.command.remove_day_from_workout;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
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
      Workout workout = repository.getById(command.workoutId());
      Workout updated = workout.removeDays(List.of(command.dayId()));
      repository.save(updated);
      publishEvent(command.toSuccessEvent());
    } catch (Exception e) {
      publishEvent(command.toFailureEvent(resolveErrorDetail(e)));
      throw e;
    }
  }
}
