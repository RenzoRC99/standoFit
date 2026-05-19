package com.standofit.back.modules.training.planning.application.command.reorder_days;

import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.EventfulCommand;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.List;

public record ReorderDaysCommand(WorkoutId workoutId, List<WorkoutDayId> dayIds)
    implements Command<Void>, EventfulCommand {

  public ApplicationEvent toSuccessEvent() {
    return PlanningActivityEvent.success(
        PlanningActivityType.WORKOUT_DAYS_REORDERED,
        workoutId.value().toString(),
        PlanningActivityType.WORKOUT_DAYS_REORDERED.getDefaultDescription());
  }

  public ApplicationEvent toFailureEvent(String errorDetail) {
    return PlanningActivityEvent.failure(
        PlanningActivityType.WORKOUT_DAYS_REORDERED,
        workoutId.value().toString(),
        PlanningActivityType.WORKOUT_DAYS_REORDERED.getDefaultDescription(),
        errorDetail);
  }
}
