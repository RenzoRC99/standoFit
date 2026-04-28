package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutDay;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutDescription;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutName;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.List;
import java.util.UUID;

public final class WorkoutMother {

  private WorkoutMother() {}

  public static WorkoutId aWorkoutId() {
    return new WorkoutId(UUID.randomUUID());
  }

  public static WorkoutName aWorkoutName() {
    return new WorkoutName("Full Body Workout");
  }

  public static WorkoutDescription aWorkoutDescription() {
    return new WorkoutDescription("Complete workout for all muscle groups");
  }

  public static Workout aWorkout() {
    return Workout.create(
        aWorkoutId(),
        aWorkoutDescription(),
        aWorkoutName(),
        List.of(WorkoutDayMother.aWorkoutDay()));
  }

  public static Workout aWorkoutWithName(String name) {
    return Workout.create(
        aWorkoutId(),
        aWorkoutDescription(),
        new WorkoutName(name),
        List.of(WorkoutDayMother.aWorkoutDay()));
  }

  public static Workout aWorkoutWithDays(List<WorkoutDay> days) {
    return Workout.create(aWorkoutId(), aWorkoutDescription(), aWorkoutName(), days);
  }

  public static void aWorkoutWithNoDays() {
    Workout.create(aWorkoutId(), aWorkoutDescription(), aWorkoutName(), List.of());
  }
}
