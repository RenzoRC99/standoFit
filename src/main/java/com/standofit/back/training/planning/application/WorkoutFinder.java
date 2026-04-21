package com.standofit.back.training.planning.application;

import com.standofit.back.training.planning.application.query.WorkoutQueryMapper;
import com.standofit.back.training.planning.application.query.dto.WorkoutDto;
import com.standofit.back.training.planning.domain.entity.Workout;
import com.standofit.back.training.planning.domain.entity.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class WorkoutFinder {

    private final WorkoutRepository repository;
    private final WorkoutQueryMapper mapper;

    public WorkoutFinder(WorkoutRepository repository, WorkoutQueryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public WorkoutDto findById(UUID id) {
        Optional<Workout> workout = repository.findById(id);
        return workout.map(mapper::toDto).orElse(null);
    }
}