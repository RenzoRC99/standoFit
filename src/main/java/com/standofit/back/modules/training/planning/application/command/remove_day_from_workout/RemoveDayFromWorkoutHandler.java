package com.standofit.back.modules.training.planning.application.command.remove_day_from_workout;

import com.standofit.back.shared.domain.bus.command.VoidCommandHandler;
import org.springframework.stereotype.Component;

@Component
public class RemoveDayFromWorkoutHandler
    implements VoidCommandHandler<RemoveDayFromWorkoutCommand> {

  private final RemoveDayFromWorkoutService service;

  public RemoveDayFromWorkoutHandler(RemoveDayFromWorkoutService service) {
    this.service = service;
  }

  @Override
  public Class<RemoveDayFromWorkoutCommand> commandType() {
    return RemoveDayFromWorkoutCommand.class;
  }

  @Override
  public void execute(RemoveDayFromWorkoutCommand command) {
    service.removeDay(command);
  }
}
