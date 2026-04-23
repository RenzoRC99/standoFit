package com.standofit.back.modules.training.planning.application.command.delete_workout;

import com.standofit.back.shared.domain.bus.command.Command;

import java.util.UUID;

public record DeleteWorkoutCommand(UUID workoutId) implements Command<Void> {
}
