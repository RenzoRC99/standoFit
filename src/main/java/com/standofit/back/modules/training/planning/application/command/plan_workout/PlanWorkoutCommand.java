package com.standofit.back.modules.training.planning.application.command.plan_workout;

import com.standofit.back.shared.domain.bus.command.Command;

import java.util.List;
import java.util.UUID;

public record PlanWorkoutCommand(
        String name,
        String description,
        List<DayInput> days
) implements Command<UUID> {

    public record DayInput(String name, List<ExerciseInput> exercises) {
    }

    public record ExerciseInput(
            UUID exerciseId,
            int sets,
            int reps,
            int restSeconds
    ) {
    }
}
