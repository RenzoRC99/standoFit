package com.standofit.back.modules.training.planning.application.command.reorder_days;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReorderDaysService extends PlanningUseCase {

  public ReorderDaysService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void reorder(ReorderDaysCommand command) {
    try {
      Workout workout = repository.getById(command.workoutId());
      Workout reordered = workout.reorderDays(command.dayIds());
      repository.save(reordered);
      publishEvent(command.toSuccessEvent());
    } catch (Exception e) {
      publishEvent(command.toFailureEvent(resolveErrorDetail(e)));
      throw e;
    }
  }
}
