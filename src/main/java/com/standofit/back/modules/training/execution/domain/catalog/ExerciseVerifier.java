package com.standofit.back.modules.training.execution.domain.catalog;

import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;

public interface ExerciseVerifier {

  boolean exists(ExerciseId id);
}
