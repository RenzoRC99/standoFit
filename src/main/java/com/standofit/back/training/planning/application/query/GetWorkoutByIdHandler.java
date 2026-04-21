package com.standofit.back.training.planning.application.query;

import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import com.standofit.back.training.planning.application.query.dto.WorkoutDto;
import com.standofit.back.training.planning.domain.entity.Workout;
import com.standofit.back.training.planning.domain.entity.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetWorkoutByIdHandler implements QueryHandler<GetWorkoutByIdQuery, WorkoutDto> {

    private final WorkoutRepository workoutRepository;
    private final WorkoutQueryMapper workoutQueryMapper;

    public GetWorkoutByIdHandler(WorkoutRepository workoutRepository, WorkoutQueryMapper workoutQueryMapper) {
        this.workoutRepository = workoutRepository;
        this.workoutQueryMapper = workoutQueryMapper;
    }

    @Override
    public WorkoutDto handle(GetWorkoutByIdQuery query) {
        Optional<Workout> workout = workoutRepository.findById(query.getWorkoutId().value());
        return workout.map(workoutQueryMapper::toDto).orElse(null);
    }
}