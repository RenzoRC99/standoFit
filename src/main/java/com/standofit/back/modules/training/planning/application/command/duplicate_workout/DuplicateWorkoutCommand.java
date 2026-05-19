package com.standofit.back.modules.training.planning.application.command.duplicate_workout;

import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.EventfulCommand;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.UUID;

public record DuplicateWorkoutCommand(WorkoutId workoutId, String newName)
    implements Command<UUID>, EventfulCommand {

  public ApplicationEvent toSuccessEvent() {
    return PlanningActivityEvent.success(
        PlanningActivityType.WORKOUT_DUPLICATED,
        workoutId.value().toString(),
        PlanningActivityType.WORKOUT_DUPLICATED.getDefaultDescription());
  }

  public ApplicationEvent toFailureEvent(String errorDetail) {
    return PlanningActivityEvent.failure(
        PlanningActivityType.WORKOUT_DUPLICATED,
        workoutId.value().toString(),
        PlanningActivityType.WORKOUT_DUPLICATED.getDefaultDescription(),
        errorDetail);
  }
}
