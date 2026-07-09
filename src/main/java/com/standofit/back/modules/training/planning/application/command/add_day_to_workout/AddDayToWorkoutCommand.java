package com.standofit.back.modules.training.planning.application.command.add_day_to_workout;

import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.EventfulCommand;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.List;

public record AddDayToWorkoutCommand(
    WorkoutId workoutId, String dayName, List<ExerciseInput> exercises)
    implements Command<Void>, EventfulCommand {

  public record ExerciseInput(ExerciseId exerciseId, int sets, int reps, int restSeconds) {}

  public ApplicationEvent toSuccessEvent() {
    return PlanningActivityEvent.success(
        PlanningActivityType.WORKOUT_DAY_ADDED,
        workoutId.value().toString(),
        PlanningActivityType.WORKOUT_DAY_ADDED.getDefaultDescription());
  }

  public ApplicationEvent toFailureEvent(String errorDetail) {
    return PlanningActivityEvent.failure(
        PlanningActivityType.WORKOUT_DAY_ADDED,
        workoutId.value().toString(),
        PlanningActivityType.WORKOUT_DAY_ADDED.getDefaultDescription(),
        errorDetail);
  }
}
