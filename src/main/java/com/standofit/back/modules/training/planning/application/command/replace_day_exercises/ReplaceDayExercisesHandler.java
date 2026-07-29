package com.standofit.back.modules.training.planning.application.command.replace_day_exercises;

import com.standofit.back.shared.domain.bus.command.VoidCommandHandler;
import org.springframework.stereotype.Component;

@Component
public class ReplaceDayExercisesHandler implements VoidCommandHandler<ReplaceDayExercisesCommand> {

  private final ReplaceDayExercisesService service;

  public ReplaceDayExercisesHandler(ReplaceDayExercisesService service) {
    this.service = service;
  }

  @Override
  public Class<ReplaceDayExercisesCommand> commandType() {
    return ReplaceDayExercisesCommand.class;
  }

  @Override
  public void execute(ReplaceDayExercisesCommand command) {
    service.replace(command);
  }
}
