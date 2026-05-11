package com.standofit.back.modules.training.planning.application.command.reorder_days;

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
public class ReorderDaysService extends PlanningUseCase {

  public ReorderDaysService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void reorder(ReorderDaysCommand command) {
    try {
      Workout workout =
          repository
              .findById(command.workoutId())
              .orElseThrow(
                  () -> new IllegalArgumentException("Workout not found: " + command.workoutId()));

      List<WorkoutDayId> dayIds = command.dayIds().stream().map(WorkoutDayId::new).toList();

      Workout reordered = workout.reorderDays(dayIds);

      repository.save(reordered);
      publishEvent(
          PlanningActivityEvent.success(
              PlanningActivityType.WORKOUT_DAYS_REORDERED,
              command.workoutId().toString(),
              PlanningActivityType.WORKOUT_DAYS_REORDERED.getDefaultDescription()));
    } catch (Exception e) {
      publishEvent(
          PlanningActivityEvent.failure(
              PlanningActivityType.WORKOUT_DAYS_REORDERED,
              command.workoutId().toString(),
              PlanningActivityType.WORKOUT_DAYS_REORDERED.getDefaultDescription(),
              e.getMessage()));
      throw e;
    }
  }
}
