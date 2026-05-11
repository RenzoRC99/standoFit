package com.standofit.back.modules.training.execution.application;

import com.standofit.back.shared.domain.ApplicationException;

public class ApplicationExecutionException extends ApplicationException {

  public ApplicationExecutionException(
      ExecutionApplicationError error, String entityId, Throwable cause) {
    super(
        ApplicationExecutionException.class,
        error.name() + ":" + entityId + " " + error.getMessage(),
        cause);
  }
}
