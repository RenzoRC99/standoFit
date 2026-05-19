package com.standofit.back.modules.training.planning.domain.entity;

import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.List;
import java.util.Optional;

public interface WorkoutRepository {

  Workout save(Workout workout);

  Optional<Workout> findById(WorkoutId id);

  Workout getById(WorkoutId id);

  List<Workout> findAll();

  List<Workout> searchByCriteria(Criteria criteria);

  long countByCriteria(Criteria criteria);

  void deleteById(WorkoutId id);
}
