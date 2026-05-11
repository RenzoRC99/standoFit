package com.standofit.back.modules.training.planning.application;

import com.standofit.back.shared.domain.ApplicationException;

public class ApplicationPlanningException extends ApplicationException {

  public ApplicationPlanningException(PlanningApplicationError error, String entityId, Throwable cause) {
    super(ApplicationPlanningException.class,
        error.name() + ":" + entityId + " " + error.getMessage(), cause);
  }
}