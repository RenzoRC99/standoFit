package com.standofit.back.modules.training.planning.application.command.archive_workout;

import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class ArchiveWorkoutHandler implements CommandHandler<ArchiveWorkoutCommand, Void> {

  private final WorkoutRepository repository;

  public ArchiveWorkoutHandler(WorkoutRepository repository) {
    this.repository = repository;
  }

  @Override
  public Class<ArchiveWorkoutCommand> commandType() {
    return ArchiveWorkoutCommand.class;
  }

  @Override
  public Void handle(ArchiveWorkoutCommand command) {
    Workout workout =
        repository
            .findById(command.workoutId())
            .orElseThrow(
                () -> new IllegalArgumentException("Workout not found: " + command.workoutId()));

    // TODO: Implement archive logic - add archived field to Workout entity
    // For now, this is a placeholder that marks the intent
    // workout.archive();
    // repository.save(workout);

    // TODO: Publish WorkoutArchivedEvent
    // eventBus.publish(new WorkoutArchivedEvent(command.workoutId()));

    return null;
  }
}
