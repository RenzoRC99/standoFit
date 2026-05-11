package com.standofit.back.modules.training.planning.application.command.remove_day_from_workout;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RemoveDayFromWorkoutHandler
    implements CommandHandler<RemoveDayFromWorkoutCommand, Void> {

  private final RemoveDayFromWorkoutService service;

  public RemoveDayFromWorkoutHandler(RemoveDayFromWorkoutService service) {
    this.service = service;
  }

  @Override
  public Class<RemoveDayFromWorkoutCommand> commandType() {
    return RemoveDayFromWorkoutCommand.class;
  }

  @Override
  @Transactional
  public Void handle(RemoveDayFromWorkoutCommand command) {
    service.removeDay(command);
    return null;
  }
}
