package com.standofit.back.modules.training.planning.application.command.rename_day;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutDayName;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class RenameDayService extends PlanningUseCase {

  public RenameDayService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void rename(RenameDayCommand command) {
    try {
      Workout workout = repository.getById(command.workoutId());
      Workout updated = workout.renameDay(command.dayId(), new WorkoutDayName(command.name()));
      repository.save(updated);
      publishEvent(command.toSuccessEvent());
    } catch (Exception e) {
      publishEvent(command.toFailureEvent(resolveErrorDetail(e)));
      throw e;
    }
  }
}
