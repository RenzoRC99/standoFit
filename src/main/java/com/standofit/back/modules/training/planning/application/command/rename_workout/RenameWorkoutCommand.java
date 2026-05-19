package com.standofit.back.modules.training.planning.application.command.rename_workout;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;

public record RenameWorkoutCommand(WorkoutId workoutId, String newName) implements Command<Void> {}
