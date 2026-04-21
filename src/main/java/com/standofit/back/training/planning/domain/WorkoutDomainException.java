package com.standofit.back.training.planning.domain;

import com.standofit.back.shared.domain.DomainException;

public class WorkoutDomainException extends DomainException {
  public WorkoutDomainException(String message) {
    super(message);
  }
}
