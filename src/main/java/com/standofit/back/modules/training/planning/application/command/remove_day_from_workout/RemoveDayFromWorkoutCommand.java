package com.standofit.back.modules.training.planning.application.command.remove_day_from_workout;

import com.standofit.back.shared.domain.bus.command.Command;
import java.util.UUID;

public record RemoveDayFromWorkoutCommand(UUID workoutId, UUID dayId) implements Command<Void> {}
