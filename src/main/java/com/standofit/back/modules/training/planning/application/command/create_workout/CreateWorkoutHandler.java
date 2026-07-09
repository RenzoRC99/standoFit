package com.standofit.back.modules.training.planning.application.command.create_workout;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CreateWorkoutHandler implements CommandHandler<CreateWorkoutCommand, UUID> {

  private final CreateWorkoutService service;

  public CreateWorkoutHandler(CreateWorkoutService service) {
    this.service = service;
  }

  @Override
  public Class<CreateWorkoutCommand> commandType() {
    return CreateWorkoutCommand.class;
  }

  @Override
  public UUID handle(CreateWorkoutCommand command) {
    return service.create(command);
  }
}
