package com.standofit.back.modules.training.planning.application.command.reorder_days;

import com.standofit.back.shared.domain.bus.command.VoidCommandHandler;
import org.springframework.stereotype.Component;

@Component
public class ReorderDaysHandler implements VoidCommandHandler<ReorderDaysCommand> {

  private final ReorderDaysService service;

  public ReorderDaysHandler(ReorderDaysService service) {
    this.service = service;
  }

  @Override
  public Class<ReorderDaysCommand> commandType() {
    return ReorderDaysCommand.class;
  }

  @Override
  public void execute(ReorderDaysCommand command) {
    service.reorder(command);
  }
}
