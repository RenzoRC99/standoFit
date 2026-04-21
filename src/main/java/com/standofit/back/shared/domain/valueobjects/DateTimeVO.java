package com.standofit.back.shared.domain.valueobjects;

import java.time.Instant;

public abstract class DateTimeVO extends BaseVO<Instant> {
  protected DateTimeVO(Instant value) {
    super(value);
  }
}
