package com.standofit.back.modules.training.planning.application.command.duplicate_workout;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.UUID;

public record DuplicateWorkoutCommand(WorkoutId workoutId, String newName) implements Command<UUID> {}
