package com.standofit.back.modules.training.planning.application.command.rename_workout;

import com.standofit.back.shared.domain.bus.command.VoidCommandHandler;
import org.springframework.stereotype.Component;

@Component
public class RenameWorkoutHandler implements VoidCommandHandler<RenameWorkoutCommand> {

  private final RenameWorkoutService service;

  public RenameWorkoutHandler(RenameWorkoutService service) {
    this.service = service;
  }

  @Override
  public Class<RenameWorkoutCommand> commandType() {
    return RenameWorkoutCommand.class;
  }

  @Override
  public void execute(RenameWorkoutCommand command) {
    service.rename(command);
  }
}
