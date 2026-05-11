package com.standofit.back.modules.training.execution.domain.vo;

import com.standofit.back.shared.domain.valueobjects.IntegerVO;

public class ExerciseLogReps extends IntegerVO {
  public ExerciseLogReps(int value) {
    super(value);
    isBiggerThan(1000);
  }
}
