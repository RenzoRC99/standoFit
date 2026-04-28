package com.standofit.back.modules.training.planning.application.command.reorder_days;

import com.standofit.back.shared.domain.bus.command.Command;
import java.util.List;
import java.util.UUID;

public record ReorderDaysCommand(UUID workoutId, List<UUID> dayIds) implements Command<Void> {}
