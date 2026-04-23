package com.standofit.back.modules.training.planning.application.command.rename_workout;

import com.standofit.back.shared.domain.bus.command.Command;

import java.util.UUID;

public record RenameWorkoutCommand(UUID workoutId, String newName) implements Command<Void> {
}
