package com.standofit.back.modules.training.execution.infrastructure;

import com.standofit.back.shared.domain.InfrastructureException;

public class SessionInfrastructureException extends InfrastructureException {
  public SessionInfrastructureException(String message) {
    super(SessionInfrastructureException.class, message);
  }

  public SessionInfrastructureException(String message, Throwable cause) {
    super(SessionInfrastructureException.class, message, cause);
  }
}
