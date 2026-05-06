package com.standofit.back.modules.training.planning.application.command.change_workout_description;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutDescription;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class ChangeWorkoutDescriptionService extends PlanningUseCase {

    public ChangeWorkoutDescriptionService(WorkoutRepository repository, WorkoutDtoMapper mapper, ApplicationEventBus applicationEventBus) {
        super(repository, mapper, applicationEventBus);
    }

    public void changeDescription(ChangeWorkoutDescriptionCommand command) {
        try {
            Workout workout =
                    repository
                            .findById(command.workoutId())
                            .orElseThrow(
                                    () -> new IllegalArgumentException("Workout not found: " + command.workoutId()));

            Workout updated = workout.changeDescription(new WorkoutDescription(command.description()));

            repository.save(updated);
            publishEvent(PlanningActivityEvent.success("workout.description.changed", command.workoutId().toString(), "Changed description"));
        } catch (Exception e) {
            publishEvent(PlanningActivityEvent.failure("workout.description.changed", command.workoutId().toString(), "Failed to change description", e.getMessage()));
            throw e;
        }
    }
}
