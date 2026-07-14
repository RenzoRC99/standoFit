package com.standofit.back.modules.training.planning.application.command.rename_day;

import com.standofit.back.shared.domain.bus.command.VoidCommandHandler;
import org.springframework.stereotype.Component;

@Component
public class RenameDayHandler implements VoidCommandHandler<RenameDayCommand> {

  private final RenameDayService service;

  public RenameDayHandler(RenameDayService service) {
    this.service = service;
  }

  @Override
  public Class<RenameDayCommand> commandType() {
    return RenameDayCommand.class;
  }

  @Override
  public void execute(RenameDayCommand command) {
    service.rename(command);
  }
}
