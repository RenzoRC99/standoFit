package com.standofit.back.modules.training.planning.application.command.delete_workout;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class DeleteWorkoutService extends PlanningUseCase {

  public DeleteWorkoutService(WorkoutRepository repository, WorkoutDtoMapper mapper, ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void delete(DeleteWorkoutCommand command) {
    try {
      repository.deleteById(command.workoutId());
      publishEvent(PlanningActivityEvent.success("workout.deleted", command.workoutId().toString(), "Deleted workout"));
    } catch (Exception e) {
      publishEvent(PlanningActivityEvent.failure("workout.deleted", command.workoutId().toString(), "Failed to delete workout", e.getMessage()));
      throw e;
    }
  }
}
