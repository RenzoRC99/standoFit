package com.standofit.back.modules.training.planning.application.command.remove_day_from_workout;

import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.EventfulCommand;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;

public record RemoveDayFromWorkoutCommand(WorkoutId workoutId, WorkoutDayId dayId)
    implements Command<Void>, EventfulCommand {

  public ApplicationEvent toSuccessEvent() {
    return PlanningActivityEvent.success(
        PlanningActivityType.WORKOUT_DAY_REMOVED,
        workoutId.value().toString(),
        PlanningActivityType.WORKOUT_DAY_REMOVED.getDefaultDescription());
  }

  public ApplicationEvent toFailureEvent(String errorDetail) {
    return PlanningActivityEvent.failure(
        PlanningActivityType.WORKOUT_DAY_REMOVED,
        workoutId.value().toString(),
        PlanningActivityType.WORKOUT_DAY_REMOVED.getDefaultDescription(),
        errorDetail);
  }
}
