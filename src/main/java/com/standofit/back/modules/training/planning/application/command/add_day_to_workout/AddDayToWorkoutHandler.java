package com.standofit.back.modules.training.planning.application.command.add_day_to_workout;

import com.standofit.back.shared.domain.bus.command.VoidCommandHandler;
import org.springframework.stereotype.Component;

@Component
public class AddDayToWorkoutHandler implements VoidCommandHandler<AddDayToWorkoutCommand> {

  private final AddDayToWorkoutService service;

  public AddDayToWorkoutHandler(AddDayToWorkoutService service) {
    this.service = service;
  }

  @Override
  public Class<AddDayToWorkoutCommand> commandType() {
    return AddDayToWorkoutCommand.class;
  }

  @Override
  public void execute(AddDayToWorkoutCommand command) {
    service.addDay(command);
  }
}
