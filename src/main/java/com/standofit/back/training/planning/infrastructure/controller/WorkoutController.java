package com.standofit.back.training.planning.infrastructure.controller;

import com.standofit.back.shared.domain.bus.query.QueryBus;
import com.standofit.back.shared.infraestructure.controller.SharedController;
import com.standofit.back.training.planning.application.query.GetWorkoutByIdQuery;
import com.standofit.back.training.planning.application.query.dto.WorkoutDto;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController extends SharedController {

    protected WorkoutController(QueryBus queryBus) {
        super(queryBus);
    }

    @GetMapping("/{id}")
    public WorkoutDto getById(@PathVariable UUID id) {
        return ask(new GetWorkoutByIdQuery(id));
    }
}
