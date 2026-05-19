package com.standofit.back.modules.training.planning.application.command.change_workout_description;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ChangeWorkoutDescriptionHandler
    implements CommandHandler<ChangeWorkoutDescriptionCommand, Void> {

  private final ChangeWorkoutDescriptionService service;

  public ChangeWorkoutDescriptionHandler(ChangeWorkoutDescriptionService service) {
    this.service = service;
  }

  @Override
  public Class<ChangeWorkoutDescriptionCommand> commandType() {
    return ChangeWorkoutDescriptionCommand.class;
  }

  @Override
  public Void handle(ChangeWorkoutDescriptionCommand command) {
    service.changeDescription(command);
    return null;
  }
}
