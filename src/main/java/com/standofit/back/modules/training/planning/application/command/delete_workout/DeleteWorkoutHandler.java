package com.standofit.back.modules.training.planning.application.command.delete_workout;
import org.springframework.transaction.annotation.Transactional;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteWorkoutHandler implements CommandHandler<DeleteWorkoutCommand, Void> {

  private final DeleteWorkoutService service;

  public DeleteWorkoutHandler(DeleteWorkoutService service) {
    this.service = service;
  }

  @Override
  public Class<DeleteWorkoutCommand> commandType() {
    return DeleteWorkoutCommand.class;
  }

  @Override
  @Transactional
  public Void handle(DeleteWorkoutCommand command) {
    service.delete(command);
    return null;
  }
}
