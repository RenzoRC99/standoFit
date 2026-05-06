package com.standofit.back.modules.training.planning.application.command.remove_day_from_workout;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemoveDayFromWorkoutService extends PlanningUseCase {

    public RemoveDayFromWorkoutService(WorkoutRepository repository, WorkoutDtoMapper mapper, ApplicationEventBus applicationEventBus) {
        super(repository, mapper, applicationEventBus);
    }

    public void removeDay(RemoveDayFromWorkoutCommand command) {
        try {
            Workout workout =
                    repository
                            .findById(command.workoutId())
                            .orElseThrow(
                                    () -> new IllegalArgumentException("Workout not found: " + command.workoutId()));

            Workout updated = workout.removeDays(List.of(new WorkoutDayId(command.dayId())));

            repository.save(updated);
            publishEvent(PlanningActivityEvent.success("workout.day.removed", command.workoutId().toString(), "Removed day: " + command.dayId()));
        } catch (Exception e) {
            publishEvent(PlanningActivityEvent.failure("workout.day.removed", command.workoutId().toString(), "Failed to remove day", e.getMessage()));
            throw e;
        }
    }
}
