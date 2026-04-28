package com.standofit.back.modules.training.planning.domain.entity;

import com.standofit.back.shared.domain.criteria.Criteria;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkoutRepository {

  Workout save(Workout workout);

  Optional<Workout> findById(UUID id);

  List<Workout> findAll();

  List<Workout> searchByCriteria(Criteria criteria);

  long countByCriteria(Criteria criteria);

  void deleteById(UUID id);
}
