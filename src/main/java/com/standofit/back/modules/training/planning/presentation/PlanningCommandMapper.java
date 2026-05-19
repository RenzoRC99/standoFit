package com.standofit.back.modules.training.planning.presentation;

import com.standofit.back.api.planning.dto.AddDayRequest;
import com.standofit.back.api.planning.dto.PlanWorkoutRequest;
import com.standofit.back.modules.training.planning.application.command.add_day_to_workout.AddDayToWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.create_workout.CreateWorkoutCommand;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.UUID;

public class PlanningCommandMapper {

  public static AddDayToWorkoutCommand toCommand(UUID workoutId, AddDayRequest request) {
    var exercises =
        request.getExercises().stream()
            .map(
                e ->
                    new AddDayToWorkoutCommand.ExerciseInput(
                        new ExerciseId(e.getExerciseId()), e.getSets(), e.getReps(), e.getRestSeconds()))
            .toList();
    return new AddDayToWorkoutCommand(new WorkoutId(workoutId), request.getDayName(), exercises);
  }

  public static CreateWorkoutCommand toCreateCommand(PlanWorkoutRequest request) {
    var days =
        request.getDays().stream()
            .map(
                day ->
                    new CreateWorkoutCommand.DayInput(
                        day.getName(),
                        day.getExercises().stream()
                            .map(
                                ex ->
                                    new CreateWorkoutCommand.ExerciseInput(
                                        ex.getExerciseId(),
                                        ex.getSets(),
                                        ex.getReps(),
                                        ex.getRestSeconds()))
                            .toList()))
            .toList();
    return new CreateWorkoutCommand(request.getName(), request.getDescription(), days);
  }
}
