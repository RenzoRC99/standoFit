package com.standofit.back.modules.training.planning.application.command.delete_workout;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;

public record DeleteWorkoutCommand(WorkoutId workoutId) implements Command<Void> {}
