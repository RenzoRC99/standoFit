package com.standofit.back.modules.training.planning.application.command.create_workout;

import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.EventfulCommand;
import java.util.List;
import java.util.UUID;

public record CreateWorkoutCommand(String name, String description, List<DayInput> days)
    implements Command<UUID>, EventfulCommand {

  public record DayInput(String name, List<ExerciseInput> exercises) {}

  public record ExerciseInput(UUID exerciseId, int sets, int reps, int restSeconds) {}

  public ApplicationEvent toSuccessEvent() {
    return PlanningActivityEvent.success(
        PlanningActivityType.WORKOUT_PLANNED,
        name,
        PlanningActivityType.WORKOUT_PLANNED.getDefaultDescription());
  }

  public ApplicationEvent toFailureEvent(String errorDetail) {
    return PlanningActivityEvent.failure(
        PlanningActivityType.WORKOUT_PLANNED,
        name,
        PlanningActivityType.WORKOUT_PLANNED.getDefaultDescription(),
        errorDetail);
  }
}
