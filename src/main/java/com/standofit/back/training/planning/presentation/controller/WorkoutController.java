package com.standofit.back.training.planning.presentation.controller;

import com.standofit.back.shared.domain.bus.ApplicationBus;
import com.standofit.back.shared.infraestructure.controller.SharedController;
import com.standofit.back.training.planning.application.command.create_workout.CreateWorkoutCommand;
import com.standofit.back.training.planning.application.dto.WorkoutDto;
import com.standofit.back.training.planning.application.query.get_workout_by_id.GetWorkoutByIdQuery;
import com.standofit.back.training.planning.presentation.dto.CreateWorkoutRequest;
import com.standofit.back.training.planning.presentation.mapper.CreateWorkoutCommandMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController extends SharedController {

    private final CreateWorkoutCommandMapper commandMapper;

    public WorkoutController(ApplicationBus applicationBus, CreateWorkoutCommandMapper commandMapper) {
        super(applicationBus);
        this.commandMapper = commandMapper;
    }

    @GetMapping("/{id}")
    public WorkoutDto getById(@PathVariable UUID id) {
        return ask(new GetWorkoutByIdQuery(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID create(@RequestBody CreateWorkoutRequest request) {
        CreateWorkoutCommand command = commandMapper.toCommand(request);
        return execute(command);
    }
}
