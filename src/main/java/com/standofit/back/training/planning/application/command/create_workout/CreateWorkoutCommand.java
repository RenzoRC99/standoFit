package com.standofit.back.training.planning.application.command.create_workout;

import com.standofit.back.shared.domain.bus.command.Command;

import java.util.List;
import java.util.UUID;

public record CreateWorkoutCommand(
        String name,
        String description,
        List<DayInput> days
) implements Command<UUID> {

    public record DayInput(String name, List<ExerciseInput> exercises) {}

    public record ExerciseInput(
            UUID exerciseId,
            int sets,
            int reps,
            int restSeconds
    ) {}
}