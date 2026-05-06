package com.standofit.back.modules.training.planning.application.command.rename_workout;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutName;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class RenameWorkoutService extends PlanningUseCase {

    public RenameWorkoutService(WorkoutRepository repository, WorkoutDtoMapper mapper, ApplicationEventBus applicationEventBus) {
        super(repository, mapper, applicationEventBus);
    }

    public void rename(RenameWorkoutCommand command) {
        try {
            Workout workout =
                    repository
                            .findById(command.workoutId())
                            .orElseThrow(
                                    () -> new IllegalArgumentException("Workout not found: " + command.workoutId()));

            Workout renamed = workout.renameWorkout(new WorkoutName(command.newName()));

            repository.save(renamed);
            publishEvent(PlanningActivityEvent.success("workout.renamed", command.workoutId().toString(), "Renamed to: " + command.newName()));
        } catch (Exception e) {
            publishEvent(PlanningActivityEvent.failure("workout.renamed", command.workoutId().toString(), "Failed to rename workout", e.getMessage()));
            throw e;
        }
    }
}
