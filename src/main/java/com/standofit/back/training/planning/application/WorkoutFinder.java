package com.standofit.back.training.planning.application;

import com.standofit.back.training.planning.application.query.GetWorkoutByIdQuery;
import com.standofit.back.training.planning.application.query.GetWorkoutByIdHandler;
import com.standofit.back.training.planning.application.query.dto.WorkoutDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkoutFinder {

    private final GetWorkoutByIdHandler getWorkoutByIdHandler;

    public WorkoutFinder(GetWorkoutByIdHandler getWorkoutByIdHandler) {
        this.getWorkoutByIdHandler = getWorkoutByIdHandler;
    }

    public WorkoutDto findById(UUID id) {
        return getWorkoutByIdHandler.handle(new GetWorkoutByIdQuery(id));
    }
}