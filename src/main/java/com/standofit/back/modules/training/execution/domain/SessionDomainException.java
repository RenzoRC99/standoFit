package com.standofit.back.modules.training.execution.domain;

import com.standofit.back.shared.domain.DomainException;

public class SessionDomainException extends DomainException {
  public SessionDomainException(String message) {
    super(SessionDomainException.class, message);
  }
}
