package com.standofit.back.modules.training.planning.application.command.delete_workout;

import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class DeleteWorkoutHandler implements CommandHandler<DeleteWorkoutCommand, Void> {

  private final WorkoutRepository repository;

  public DeleteWorkoutHandler(WorkoutRepository repository) {
    this.repository = repository;
  }

  @Override
  public Class<DeleteWorkoutCommand> commandType() {
    return DeleteWorkoutCommand.class;
  }

  @Override
  public Void handle(DeleteWorkoutCommand command) {
    // TODO: Publish WorkoutDeletedEvent
    // eventBus.publish(new WorkoutDeletedEvent(command.workoutId()));

    repository.deleteById(command.workoutId());
    return null;
  }
}
