package com.standofit.back.modules.training.planning.application.command.rename_day;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;

public record RenameDayCommand(WorkoutId workoutId, WorkoutDayId dayId, String name)
    implements Command<Void> {}
