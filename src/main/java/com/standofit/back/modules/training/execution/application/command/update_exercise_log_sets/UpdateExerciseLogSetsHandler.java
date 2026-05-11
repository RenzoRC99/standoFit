package com.standofit.back.modules.training.execution.application.command.update_exercise_log_sets;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateExerciseLogSetsHandler
    implements CommandHandler<UpdateExerciseLogSetsCommand, Void> {

  private final UpdateExerciseLogSetsService service;

  public UpdateExerciseLogSetsHandler(UpdateExerciseLogSetsService service) {
    this.service = service;
  }

  @Override
  public Class<UpdateExerciseLogSetsCommand> commandType() {
    return UpdateExerciseLogSetsCommand.class;
  }

  @Override
  @Transactional
  public Void handle(UpdateExerciseLogSetsCommand command) {
    service.updateSets(command);
    return null;
  }
}
