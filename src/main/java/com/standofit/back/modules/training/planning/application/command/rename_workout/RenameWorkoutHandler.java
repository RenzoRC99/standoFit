package com.standofit.back.modules.training.planning.application.command.rename_workout;

import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutName;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class RenameWorkoutHandler implements CommandHandler<RenameWorkoutCommand, Void> {

  private final WorkoutRepository repository;

  public RenameWorkoutHandler(WorkoutRepository repository) {
    this.repository = repository;
  }

  @Override
  public Class<RenameWorkoutCommand> commandType() {
    return RenameWorkoutCommand.class;
  }

  @Override
  public Void handle(RenameWorkoutCommand command) {
    Workout workout =
        repository
            .findById(command.workoutId())
            .orElseThrow(
                () -> new IllegalArgumentException("Workout not found: " + command.workoutId()));

    Workout renamed = workout.renameWorkout(new WorkoutName(command.newName()));

    // TODO: Publish WorkoutRenamedEvent
    // eventBus.publish(new WorkoutRenamedEvent(command.workoutId(), command.newName()));

    repository.save(renamed);
    return null;
  }
}
