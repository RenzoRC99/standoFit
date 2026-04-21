package com.standofit.back.training.planning.application.query.get_workout_by_id;

import com.standofit.back.training.planning.domain.entity.Workout;
import com.standofit.back.training.planning.domain.entity.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class WorkoutFinder {

    private final WorkoutRepository repository;

    public WorkoutFinder(WorkoutRepository repository) {
        this.repository = repository;
    }

    public Optional<Workout> findById(UUID id) {
        return repository.findById(id);
    }
}