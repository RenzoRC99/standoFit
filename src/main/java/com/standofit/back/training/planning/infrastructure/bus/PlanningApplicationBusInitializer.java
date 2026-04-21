package com.standofit.back.training.planning.infrastructure.bus;

import com.standofit.back.shared.domain.bus.ApplicationBus;
import com.standofit.back.shared.domain.bus.InMemoryApplicationBus;
import com.standofit.back.training.planning.application.command.create_workout.CreateWorkoutHandler;
import com.standofit.back.training.planning.application.command.create_workout.CreateWorkoutCommand;
import com.standofit.back.training.planning.application.query.get_workout_by_id.GetWorkoutByIdHandler;
import com.standofit.back.training.planning.application.query.get_workout_by_id.GetWorkoutByIdQuery;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class PlanningApplicationBusInitializer {

    private final ApplicationBus applicationBus;
    private final GetWorkoutByIdHandler getWorkoutByIdHandler;
    private final CreateWorkoutHandler createWorkoutHandler;

    public PlanningApplicationBusInitializer(
            ApplicationBus applicationBus,
            GetWorkoutByIdHandler getWorkoutByIdHandler,
            CreateWorkoutHandler createWorkoutHandler) {
        this.applicationBus = applicationBus;
        this.getWorkoutByIdHandler = getWorkoutByIdHandler;
        this.createWorkoutHandler = createWorkoutHandler;
    }

    @PostConstruct
    public void registerHandlers() {
        if (applicationBus instanceof InMemoryApplicationBus bus) {
            bus.registerQueryHandler(GetWorkoutByIdQuery.class, getWorkoutByIdHandler);
            bus.registerCommandHandler(CreateWorkoutCommand.class, createWorkoutHandler);
        }
    }
}