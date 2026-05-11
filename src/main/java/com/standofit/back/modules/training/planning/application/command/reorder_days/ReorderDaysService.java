package com.standofit.back.modules.training.planning.application.command.reorder_days;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
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
              "workout.days.reordered", command.workoutId().toString(), "Days reordered"));
    } catch (Exception e) {
      publishEvent(
          PlanningActivityEvent.failure(
              "workout.days.reordered",
              command.workoutId().toString(),
              "Failed to reorder days",
              e.getMessage()));
      throw e;
    }
  }
}
