package com.standofit.back.modules.training.planning.domain.entity;

import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseReps;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseRest;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseSets;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;

public final class WorkoutExercise {
  private final WorkoutExerciseId id;
  private final ExerciseId exerciseId;
  private final WorkoutExerciseSets sets;
  private final WorkoutExerciseReps reps;
  private final WorkoutExerciseRest restSeconds;

  WorkoutExercise(
      WorkoutExerciseId id,
      ExerciseId exerciseId,
      WorkoutExerciseSets sets,
      WorkoutExerciseReps reps,
      WorkoutExerciseRest restSeconds) {
    this.id = id;
    this.exerciseId = exerciseId;
    this.sets = sets;
    this.reps = reps;
    this.restSeconds = restSeconds;
  }

  public static WorkoutExercise create(
      WorkoutExerciseId id,
      ExerciseId exerciseId,
      WorkoutExerciseSets sets,
      WorkoutExerciseReps reps,
      WorkoutExerciseRest restSeconds) {
    return new WorkoutExercise(id, exerciseId, sets, reps, restSeconds);
  }

  WorkoutExercise copy(
      WorkoutExerciseId id,
      ExerciseId exerciseId,
      WorkoutExerciseSets sets,
      WorkoutExerciseReps reps,
      WorkoutExerciseRest restSeconds) {
    return new WorkoutExercise(id, exerciseId, sets, reps, restSeconds);
  }

  public WorkoutExercise updateSets(WorkoutExerciseSets sets) {
    return copy(this.id, this.exerciseId, sets, this.reps, this.restSeconds);
  }

  public WorkoutExercise updateReps(WorkoutExerciseReps reps) {
    return copy(this.id, this.exerciseId, this.sets, reps, this.restSeconds);
  }

  public WorkoutExercise updateRestSeconds(WorkoutExerciseRest restSeconds) {
    return copy(this.id, this.exerciseId, this.sets, this.reps, restSeconds);
  }

  public WorkoutExerciseId getId() {
    return id;
  }

  public ExerciseId getExerciseId() {
    return exerciseId;
  }

  public WorkoutExerciseSets getSets() {
    return sets;
  }

  public WorkoutExerciseReps getReps() {
    return reps;
  }

  public WorkoutExerciseRest getRestSeconds() {
    return restSeconds;
  }
}
