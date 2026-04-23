package com.standofit.back.modules.training.planning.application.command.archive_workout;

import com.standofit.back.shared.domain.bus.command.Command;

import java.util.UUID;

public record ArchiveWorkoutCommand(UUID workoutId) implements Command<Void> {
}
