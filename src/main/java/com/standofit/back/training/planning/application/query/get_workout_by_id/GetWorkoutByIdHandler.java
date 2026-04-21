package com.standofit.back.training.planning.application.query.get_workout_by_id;

import com.standofit.back.shared.domain.bus.query.QueryHandler;
import com.standofit.back.training.planning.application.dto.WorkoutDto;
import com.standofit.back.training.planning.application.mapper.WorkoutDtoMapper;
import org.springframework.stereotype.Service;

@Service
public class GetWorkoutByIdHandler implements QueryHandler<GetWorkoutByIdQuery, WorkoutDto> {

    private final WorkoutFinder finder;
    private final WorkoutDtoMapper mapper;

    public GetWorkoutByIdHandler(WorkoutFinder finder, WorkoutDtoMapper mapper) {
        this.finder = finder;
        this.mapper = mapper;
    }

    @Override
    public WorkoutDto handle(GetWorkoutByIdQuery query) {
        return finder.findById(query.getWorkoutId().value())
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Workout not found: " + query.getWorkoutId().value()));
    }
}