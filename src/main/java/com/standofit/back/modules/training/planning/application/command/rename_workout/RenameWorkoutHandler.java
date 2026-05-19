package com.standofit.back.modules.training.planning.application.command.rename_workout;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RenameWorkoutHandler implements CommandHandler<RenameWorkoutCommand, Void> {

  private final RenameWorkoutService service;

  public RenameWorkoutHandler(RenameWorkoutService service) {
    this.service = service;
  }

  @Override
  public Class<RenameWorkoutCommand> commandType() {
    return RenameWorkoutCommand.class;
  }

  @Override
  public Void handle(RenameWorkoutCommand command) {
    service.rename(command);
    return null;
  }
}
