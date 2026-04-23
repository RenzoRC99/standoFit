package com.standofit.back.modules.training.planning.application.command.change_workout_description;

import com.standofit.back.shared.domain.bus.command.Command;

import java.util.UUID;

public record ChangeWorkoutDescriptionCommand(UUID workoutId, String description) implements Command<Void> {
}
