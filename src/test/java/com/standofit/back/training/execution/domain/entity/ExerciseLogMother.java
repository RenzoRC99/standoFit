package com.standofit.back.training.execution.domain.entity;

import com.standofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogReps;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogSets;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogWeight;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import java.util.UUID;

public final class ExerciseLogMother {

  private ExerciseLogMother() {}

  public static ExerciseLogId anExerciseLogId() {
    return new ExerciseLogId(UUID.randomUUID());
  }

  public static ExerciseId anExerciseId() {
    return new ExerciseId(UUID.randomUUID());
  }

  public static ExerciseLogSets defaultSets() {
    return new ExerciseLogSets(3);
  }

  public static ExerciseLogReps defaultReps() {
    return new ExerciseLogReps(10);
  }

  public static ExerciseLogWeight defaultWeight() {
    return new ExerciseLogWeight(50);
  }

  public static ExerciseLog anExerciseLog() {
    return ExerciseLog.create(
        anExerciseLogId(), anExerciseId(), defaultSets(), defaultReps(), defaultWeight());
  }

  public static ExerciseLog anExerciseLogWithExerciseId(ExerciseId exerciseId) {
    return ExerciseLog.create(
        anExerciseLogId(), exerciseId, defaultSets(), defaultReps(), defaultWeight());
  }

  public static ExerciseLog anExerciseLogWith(int sets, int reps, int weight) {
    return ExerciseLog.create(
        anExerciseLogId(),
        anExerciseId(),
        new ExerciseLogSets(sets),
        new ExerciseLogReps(reps),
        new ExerciseLogWeight(weight));
  }

  public static ExerciseLog anExerciseLogWith(
      ExerciseId exerciseId, int sets, int reps, int weight) {
    return ExerciseLog.create(
        anExerciseLogId(),
        exerciseId,
        new ExerciseLogSets(sets),
        new ExerciseLogReps(reps),
        new ExerciseLogWeight(weight));
  }
}
