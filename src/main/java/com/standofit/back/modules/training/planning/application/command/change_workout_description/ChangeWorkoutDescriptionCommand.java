package com.standofit.back.modules.training.planning.application.command.change_workout_description;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;

public record ChangeWorkoutDescriptionCommand(WorkoutId workoutId, String description)
    implements Command<Void> {}
