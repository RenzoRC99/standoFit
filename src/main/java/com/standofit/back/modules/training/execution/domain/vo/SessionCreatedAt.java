package com.standofit.back.modules.training.execution.domain.vo;

import com.standofit.back.shared.domain.valueobjects.DateTimeVO;
import java.time.Instant;

public class SessionCreatedAt extends DateTimeVO {
  public SessionCreatedAt(Instant value) {
    super(value);
  }
}
