package com.standofit.back.modules.training.planning.presentation;

import com.standofit.back.api.planning.dto.AddDayRequest;
import com.standofit.back.api.planning.dto.PlanWorkoutRequest;
import com.standofit.back.modules.training.planning.application.command.add_day_to_workout.AddDayToWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.plan_workout.PlanWorkoutCommand;
import java.util.UUID;

public class PlanningCommandMapper {

  public static AddDayToWorkoutCommand toCommand(UUID workoutId, AddDayRequest request) {
    var exercises =
        request.getExercises().stream()
            .map(
                e ->
                    new AddDayToWorkoutCommand.ExerciseInput(
                        e.getExerciseId(), e.getSets(), e.getReps(), e.getRestSeconds()))
            .toList();
    return new AddDayToWorkoutCommand(workoutId, request.getDayName(), exercises);
  }

  public static PlanWorkoutCommand toCommand(PlanWorkoutRequest request) {
    var days =
        request.getDays().stream()
            .map(
                day ->
                    new PlanWorkoutCommand.DayInput(
                        day.getName(),
                        day.getExercises().stream()
                            .map(
                                ex ->
                                    new PlanWorkoutCommand.ExerciseInput(
                                        ex.getExerciseId(),
                                        ex.getSets(),
                                        ex.getReps(),
                                        ex.getRestSeconds()))
                            .toList()))
            .toList();
    return new PlanWorkoutCommand(request.getName(), request.getDescription(), days);
  }
}
