package com.standofit.back.training.planning.application.query;

import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import com.standofit.back.training.planning.application.WorkoutFinder;
import com.standofit.back.training.planning.application.query.dto.WorkoutDto;
import org.springframework.stereotype.Service;

@Service
public class GetWorkoutByIdHandler implements QueryHandler<GetWorkoutByIdQuery, WorkoutDto> {

    private final WorkoutFinder finder;

    public GetWorkoutByIdHandler(WorkoutFinder finder) {
        this.finder = finder;
    }

    @Override
    public WorkoutDto handle(GetWorkoutByIdQuery query) {
        return finder.findById(query.getWorkoutId().value());
    }
}