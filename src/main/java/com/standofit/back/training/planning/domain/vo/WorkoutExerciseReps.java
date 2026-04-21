package com.standofit.back.training.planning.domain.vo;

import com.standofit.back.shared.domain.valueobjects.IntegerVO;

public class WorkoutExerciseReps extends IntegerVO {
  public WorkoutExerciseReps(Integer value) {
    super(value);
    isAtLeast(1);
    isBiggerThan(1000);
  }
}
