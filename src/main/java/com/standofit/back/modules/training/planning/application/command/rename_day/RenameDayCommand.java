package com.standofit.back.modules.training.planning.application.command.rename_day;

import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.EventfulCommand;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;

public record RenameDayCommand(WorkoutId workoutId, WorkoutDayId dayId, String name)
    implements Command<Void>, EventfulCommand {

  public ApplicationEvent toSuccessEvent() {
    return PlanningActivityEvent.success(
        PlanningActivityType.WORKOUT_DAY_RENAMED,
        workoutId.value().toString(),
        PlanningActivityType.WORKOUT_DAY_RENAMED.getDefaultDescription());
  }

  public ApplicationEvent toFailureEvent(String errorDetail) {
    return PlanningActivityEvent.failure(
        PlanningActivityType.WORKOUT_DAY_RENAMED,
        workoutId.value().toString(),
        PlanningActivityType.WORKOUT_DAY_RENAMED.getDefaultDescription(),
        errorDetail);
  }
}
