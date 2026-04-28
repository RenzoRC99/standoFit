package com.standofit.back.modules.training.planning.application.command.reorder_days;

import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReorderDaysHandler implements CommandHandler<ReorderDaysCommand, Void> {

  private final WorkoutRepository repository;

  public ReorderDaysHandler(WorkoutRepository repository) {
    this.repository = repository;
  }

  @Override
  public Class<ReorderDaysCommand> commandType() {
    return ReorderDaysCommand.class;
  }

  @Override
  public Void handle(ReorderDaysCommand command) {
    Workout workout =
        repository
            .findById(command.workoutId())
            .orElseThrow(
                () -> new IllegalArgumentException("Workout not found: " + command.workoutId()));

    List<WorkoutDayId> dayIds = command.dayIds().stream().map(WorkoutDayId::new).toList();

    Workout reordered = workout.reorderDays(dayIds);

    // TODO: Publish DaysReorderedEvent
    // eventBus.publish(new DaysReorderedEvent(command.workoutId(), command.dayIds()));

    repository.save(reordered);
    return null;
  }
}
