package com.standofit.back.modules.training.planning.application.command.replace_day_exercises;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReplaceDayExercisesHandler implements CommandHandler<ReplaceDayExercisesCommand, Void> {

  private final ReplaceDayExercisesService service;

  public ReplaceDayExercisesHandler(ReplaceDayExercisesService service) {
    this.service = service;
  }

  @Override
  public Class<ReplaceDayExercisesCommand> commandType() {
    return ReplaceDayExercisesCommand.class;
  }

  @Override
  public Void handle(ReplaceDayExercisesCommand command) {
    service.replace(command);
    return null;
  }
}
