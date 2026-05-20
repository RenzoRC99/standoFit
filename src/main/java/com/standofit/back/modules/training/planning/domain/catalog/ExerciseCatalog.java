package com.standofit.back.modules.training.planning.domain.catalog;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Repository interface for accessing exercise catalog data.
 * Defined in planning domain, implemented in infrastructure.
 * This decouples planning module from exercises module.
 */
public interface ExerciseCatalog {

  /**
   * Finds exercise names and muscle groups by their IDs.
   *
   * @param ids set of exercise IDs
   * @return ExerciseData containing name and muscleGroup lookup maps
   */
  ExerciseData findByIds(Set<UUID> ids);

  /**
   * Finds a single exercise by ID.
   *
   * @param id exercise ID
   * @return optional containing ExerciseInfo if found
   */
  Optional<ExerciseInfo> findById(UUID id);

  /**
   * Container for exercise enrichment data returned by findByIds.
   */
  record ExerciseData(Map<UUID, String> names, Map<UUID, String> muscleGroups) {}

  /**
   * Value object containing exercise information needed by planning module.
   */
  record ExerciseInfo(UUID id, String name, String muscleGroup) {}
}
