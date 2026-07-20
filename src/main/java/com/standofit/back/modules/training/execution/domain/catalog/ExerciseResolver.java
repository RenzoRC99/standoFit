package com.standofit.back.modules.training.execution.domain.catalog;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/** Port for resolving exercise catalog data. Decouples execution module from exercises module. */
public interface ExerciseResolver {

  Map<UUID, ExerciseInfo> resolve(Set<UUID> ids);

  record ExerciseInfo(String name, String muscleGroup) {}
}
