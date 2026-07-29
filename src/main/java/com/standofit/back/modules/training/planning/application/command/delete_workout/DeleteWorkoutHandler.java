package com.standofit.back.modules.training.planning.application.command.delete_workout;

import com.standofit.back.shared.domain.bus.command.VoidCommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteWorkoutHandler implements VoidCommandHandler<DeleteWorkoutCommand> {

  private final DeleteWorkoutService service;

  public DeleteWorkoutHandler(DeleteWorkoutService service) {
    this.service = service;
  }

  @Override
  public Class<DeleteWorkoutCommand> commandType() {
    return DeleteWorkoutCommand.class;
  }

  @Override
  public void execute(DeleteWorkoutCommand command) {
    service.delete(command);
  }
}
