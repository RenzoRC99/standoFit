package com.standofit.back.modules.training.planning.application.command.remove_day_from_workout;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;

public record RemoveDayFromWorkoutCommand(WorkoutId workoutId, WorkoutDayId dayId) implements Command<Void> {}
