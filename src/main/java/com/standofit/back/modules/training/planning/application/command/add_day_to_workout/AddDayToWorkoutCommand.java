package com.standofit.back.modules.training.planning.application.command.add_day_to_workout;

import com.standofit.back.shared.domain.bus.command.Command;
import java.util.List;
import java.util.UUID;

public record AddDayToWorkoutCommand(UUID workoutId, String dayName, List<ExerciseInput> exercises)
    implements Command<Void> {

  public record ExerciseInput(UUID exerciseId, int sets, int reps, int restSeconds) {}
}
