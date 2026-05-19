package com.standofit.back.modules.training.planning.application.command.reorder_days;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReorderDaysHandler implements CommandHandler<ReorderDaysCommand, Void> {

  private final ReorderDaysService service;

  public ReorderDaysHandler(ReorderDaysService service) {
    this.service = service;
  }

  @Override
  public Class<ReorderDaysCommand> commandType() {
    return ReorderDaysCommand.class;
  }

  @Override
  public Void handle(ReorderDaysCommand command) {
    service.reorder(command);
    return null;
  }
}
