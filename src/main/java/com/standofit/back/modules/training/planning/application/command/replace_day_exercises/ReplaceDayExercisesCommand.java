package com.standofit.back.modules.training.planning.application.command.replace_day_exercises;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.List;

public record ReplaceDayExercisesCommand(
    WorkoutId workoutId, WorkoutDayId dayId, List<ExerciseInput> exercises)
    implements Command<Void> {

  public record ExerciseInput(
      java.util.UUID exerciseId, int sets, int reps, int restSeconds) {}
}
