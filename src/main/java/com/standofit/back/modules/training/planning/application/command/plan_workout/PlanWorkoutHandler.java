package com.standofit.back.modules.training.planning.application.command.plan_workout;
import org.springframework.transaction.annotation.Transactional;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PlanWorkoutHandler implements CommandHandler<PlanWorkoutCommand, UUID> {

  private final PlanWorkoutService service;

  public PlanWorkoutHandler(PlanWorkoutService service) {
    this.service = service;
  }

  @Override
  public Class<PlanWorkoutCommand> commandType() {
    return PlanWorkoutCommand.class;
  }

  @Override
  @Transactional
  public UUID handle(PlanWorkoutCommand command) {
    return service.plan(command);
  }
}
