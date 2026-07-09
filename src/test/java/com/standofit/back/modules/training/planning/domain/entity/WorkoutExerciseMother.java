package com.standofit.back.modules.training.planning.domain.entity;

import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseReps;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseRest;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseSets;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import java.util.UUID;

public final class WorkoutExerciseMother {

  private WorkoutExerciseMother() {}

  public static WorkoutExerciseId aWorkoutExerciseId() {
    return new WorkoutExerciseId(UUID.randomUUID());
  }

  public static ExerciseId anExerciseId() {
    return new ExerciseId(UUID.randomUUID());
  }

  public static WorkoutExerciseSets defaultSets() {
    return new WorkoutExerciseSets(3);
  }

  public static WorkoutExerciseReps defaultReps() {
    return new WorkoutExerciseReps(10);
  }

  public static WorkoutExerciseRest defaultRest() {
    return new WorkoutExerciseRest(60);
  }

  public static WorkoutExercise aWorkoutExercise() {
    return WorkoutExercise.create(
        aWorkoutExerciseId(), anExerciseId(), defaultSets(), defaultReps(), defaultRest());
  }

  public static WorkoutExercise aWorkoutExerciseWith(int sets, int reps, int rest) {
    return WorkoutExercise.create(
        aWorkoutExerciseId(),
        anExerciseId(),
        new WorkoutExerciseSets(sets),
        new WorkoutExerciseReps(reps),
        new WorkoutExerciseRest(rest));
  }
}
