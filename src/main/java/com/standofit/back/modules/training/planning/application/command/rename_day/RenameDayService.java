package com.standofit.back.modules.training.planning.application.command.rename_day;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutDayName;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class RenameDayService extends PlanningUseCase {

  public RenameDayService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void rename(RenameDayCommand command) {
    try {
      Workout workout = repository.getById(command.workoutId());
      Workout updated = workout.renameDays(Map.of(command.dayId(), new WorkoutDayName(command.name())));
      repository.save(updated);
      publishEvent(
          PlanningActivityEvent.success(
              PlanningActivityType.WORKOUT_DAY_RENAMED,
              command.workoutId().value().toString(),
              PlanningActivityType.WORKOUT_DAY_RENAMED.getDefaultDescription()));
    } catch (Exception e) {
      publishEvent(
          PlanningActivityEvent.failure(
              PlanningActivityType.WORKOUT_DAY_RENAMED,
              command.workoutId().value().toString(),
              PlanningActivityType.WORKOUT_DAY_RENAMED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
