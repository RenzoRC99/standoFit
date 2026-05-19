package com.standofit.back.modules.training.planning.application.command.replace_day_exercises;

import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.EventfulCommand;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.List;

public record ReplaceDayExercisesCommand(
    WorkoutId workoutId, WorkoutDayId dayId, List<ExerciseInput> exercises)
    implements Command<Void>, EventfulCommand {

  public record ExerciseInput(
      java.util.UUID exerciseId, int sets, int reps, int restSeconds) {}

  public ApplicationEvent toSuccessEvent() {
    return PlanningActivityEvent.success(
        PlanningActivityType.WORKOUT_EXERCISES_REPLACED,
        workoutId.value().toString(),
        PlanningActivityType.WORKOUT_EXERCISES_REPLACED.getDefaultDescription());
  }

  public ApplicationEvent toFailureEvent(String errorDetail) {
    return PlanningActivityEvent.failure(
        PlanningActivityType.WORKOUT_EXERCISES_REPLACED,
        workoutId.value().toString(),
        PlanningActivityType.WORKOUT_EXERCISES_REPLACED.getDefaultDescription(),
        errorDetail);
  }
}
