package com.standofit.back.modules.training.planning.application.command.reorder_days;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.List;

public record ReorderDaysCommand(WorkoutId workoutId, List<WorkoutDayId> dayIds) implements Command<Void> {}
