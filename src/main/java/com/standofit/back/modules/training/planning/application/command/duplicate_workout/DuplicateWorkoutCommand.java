package com.standofit.back.modules.training.planning.application.command.duplicate_workout;

import com.standofit.back.shared.domain.bus.command.Command;

import java.util.UUID;

public record DuplicateWorkoutCommand(UUID workoutId, String newName) implements Command<UUID> {
}
