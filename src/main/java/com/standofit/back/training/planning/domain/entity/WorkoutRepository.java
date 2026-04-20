package com.standofit.back.training.planning.domain.entity;

import java.util.Optional;
import java.util.UUID;

public interface WorkoutRepository {

    Workout save(Workout workout);

    Optional<Workout> findById(UUID id);

    void deleteById(UUID id);

}
