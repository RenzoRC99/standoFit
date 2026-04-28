package com.standofit.back.modules.training.planning.domain.vo;

import com.standofit.back.shared.domain.valueobjects.StringVO;

public class WorkoutDescription extends StringVO {
  public WorkoutDescription(String value) {
    super(value);
    ensureLengthRange(0, 500);
  }
}
