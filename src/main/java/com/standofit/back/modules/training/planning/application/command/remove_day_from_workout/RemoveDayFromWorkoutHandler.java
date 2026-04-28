package com.standofit.back.modules.training.planning.application.command.remove_day_from_workout;

import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RemoveDayFromWorkoutHandler
    implements CommandHandler<RemoveDayFromWorkoutCommand, Void> {

  private final WorkoutRepository repository;

  public RemoveDayFromWorkoutHandler(WorkoutRepository repository) {
    this.repository = repository;
  }

  @Override
  public Class<RemoveDayFromWorkoutCommand> commandType() {
    return RemoveDayFromWorkoutCommand.class;
  }

  @Override
  public Void handle(RemoveDayFromWorkoutCommand command) {
    Workout workout =
        repository
            .findById(command.workoutId())
            .orElseThrow(
                () -> new IllegalArgumentException("Workout not found: " + command.workoutId()));

    Workout updated = workout.removeDays(List.of(new WorkoutDayId(command.dayId())));

    // TODO: Publish DayRemovedFromWorkoutEvent
    // eventBus.publish(new DayRemovedFromWorkoutEvent(command.workoutId(), command.dayId()));

    repository.save(updated);
    return null;
  }
}
