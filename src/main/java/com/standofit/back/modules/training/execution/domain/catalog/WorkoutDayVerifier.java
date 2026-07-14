package com.standofit.back.modules.training.execution.domain.catalog;

import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;

public interface WorkoutDayVerifier {

  boolean exists(SessionDayId id);
}
