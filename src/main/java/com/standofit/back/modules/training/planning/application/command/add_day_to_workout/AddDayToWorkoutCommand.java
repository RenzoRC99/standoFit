package com.standofit.back.modules.training.planning.application.command.add_day_to_workout;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.List;
import java.util.UUID;

public record AddDayToWorkoutCommand(WorkoutId workoutId, String dayName, List<ExerciseInput> exercises)
    implements Command<Void> {

  public record ExerciseInput(ExerciseId exerciseId, int sets, int reps, int restSeconds) {}
}
