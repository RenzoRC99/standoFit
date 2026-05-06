package com.standofit.back.modules.training.planning.application.command.archive_workout;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class ArchiveWorkoutService extends PlanningUseCase {

    public ArchiveWorkoutService(WorkoutRepository repository, WorkoutDtoMapper mapper, ApplicationEventBus applicationEventBus) {
        super(repository, mapper, applicationEventBus);
    }

    public void archive(ArchiveWorkoutCommand command) {
        try {
            Workout workout = repository.findById(command.workoutId()).orElseThrow();
            repository.save(workout);
            publishEvent(PlanningActivityEvent.success("workout.archived", command.workoutId().toString(), "Archived workout"));
        } catch (Exception e) {
            publishEvent(PlanningActivityEvent.failure("workout.archived", command.workoutId().toString(), "Failed to archive workout", e.getMessage()));
            throw e;
        }
    }
}
