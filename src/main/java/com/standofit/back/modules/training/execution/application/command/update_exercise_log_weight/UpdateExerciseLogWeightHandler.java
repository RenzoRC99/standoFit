package com.standofit.back.modules.training.execution.application.command.update_exercise_log_weight;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateExerciseLogWeightHandler
    implements CommandHandler<UpdateExerciseLogWeightCommand, Void> {

  private final UpdateExerciseLogWeightService service;

  public UpdateExerciseLogWeightHandler(UpdateExerciseLogWeightService service) {
    this.service = service;
  }

  @Override
  public Class<UpdateExerciseLogWeightCommand> commandType() {
    return UpdateExerciseLogWeightCommand.class;
  }

  @Override
  public Void handle(UpdateExerciseLogWeightCommand command) {
    service.updateWeight(command);
    return null;
  }
}
