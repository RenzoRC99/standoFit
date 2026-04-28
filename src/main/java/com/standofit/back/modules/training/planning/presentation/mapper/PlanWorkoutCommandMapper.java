package com.standofit.back.modules.training.planning.presentation.mapper;

import com.standofit.back.modules.training.planning.application.command.plan_workout.PlanWorkoutCommand;
import com.standofit.back.modules.training.planning.presentation.dto.PlanWorkoutRequest;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PlanWorkoutCommandMapper {

  public PlanWorkoutCommand toCommand(PlanWorkoutRequest request) {
    return new PlanWorkoutCommand(
        request.name(),
        request.description(),
        request.days().stream()
            .map(
                day ->
                    new PlanWorkoutCommand.DayInput(
                        day.name(),
                        day.exercises().stream()
                            .map(
                                ex ->
                                    new PlanWorkoutCommand.ExerciseInput(
                                        ex.exerciseId(), ex.sets(), ex.reps(), ex.restSeconds()))
                            .collect(Collectors.toList())))
            .collect(Collectors.toList()));
  }
}
