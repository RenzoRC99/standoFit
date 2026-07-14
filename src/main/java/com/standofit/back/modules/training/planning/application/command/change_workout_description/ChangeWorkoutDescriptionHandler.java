package com.standofit.back.modules.training.planning.application.command.change_workout_description;

import com.standofit.back.shared.domain.bus.command.VoidCommandHandler;
import org.springframework.stereotype.Component;

@Component
public class ChangeWorkoutDescriptionHandler
    implements VoidCommandHandler<ChangeWorkoutDescriptionCommand> {

  private final ChangeWorkoutDescriptionService service;

  public ChangeWorkoutDescriptionHandler(ChangeWorkoutDescriptionService service) {
    this.service = service;
  }

  @Override
  public Class<ChangeWorkoutDescriptionCommand> commandType() {
    return ChangeWorkoutDescriptionCommand.class;
  }

  @Override
  public void execute(ChangeWorkoutDescriptionCommand command) {
    service.changeDescription(command);
  }
}
