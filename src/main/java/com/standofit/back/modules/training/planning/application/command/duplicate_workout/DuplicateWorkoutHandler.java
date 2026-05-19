package com.standofit.back.modules.training.planning.application.command.duplicate_workout;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DuplicateWorkoutHandler implements CommandHandler<DuplicateWorkoutCommand, UUID> {

  private final DuplicateWorkoutService service;

  public DuplicateWorkoutHandler(DuplicateWorkoutService service) {
    this.service = service;
  }

  @Override
  public Class<DuplicateWorkoutCommand> commandType() {
    return DuplicateWorkoutCommand.class;
  }

  @Override
  public UUID handle(DuplicateWorkoutCommand command) {
    return service.duplicate(command);
  }
}
