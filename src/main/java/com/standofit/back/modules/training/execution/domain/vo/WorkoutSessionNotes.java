package com.standofit.back.modules.training.execution.domain.vo;

import com.standofit.back.shared.domain.valueobjects.StringVO;

public class WorkoutSessionNotes extends StringVO {
  public WorkoutSessionNotes(String value) {
    super(value);
    ensureLengthRange(0, 1000);
  }
}
