package com.standofit.back.training.planning.presentation.mapper;

import com.standofit.back.training.planning.application.command.create_workout.CreateWorkoutCommand;
import com.standofit.back.training.planning.presentation.dto.CreateWorkoutRequest;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CreateWorkoutCommandMapper {

    public CreateWorkoutCommand toCommand(CreateWorkoutRequest request) {
        return new CreateWorkoutCommand(
                request.name(),
                request.description(),
                request.days().stream()
                        .map(day -> new CreateWorkoutCommand.DayInput(
                                day.name(),
                                day.exercises().stream()
                                        .map(ex -> new CreateWorkoutCommand.ExerciseInput(
                                                ex.exerciseId(),
                                                ex.sets(),
                                                ex.reps(),
                                                ex.restSeconds()))
                                        .collect(Collectors.toList())))
                        .collect(Collectors.toList()));
    }
}