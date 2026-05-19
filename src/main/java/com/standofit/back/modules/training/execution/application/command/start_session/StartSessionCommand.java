package com.standofit.back.modules.training.execution.application.command.start_session;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.EventfulCommand;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import java.util.UUID;

public record StartSessionCommand(SessionDayId dayId) implements Command<UUID>, EventfulCommand {

  public ApplicationEvent toSuccessEvent() {
    return SessionActivityEvent.success(
        ExecutionActivityType.SESSION_STARTED,
        dayId.value().toString(),
        ExecutionActivityType.SESSION_STARTED.getDefaultDescription());
  }

  public ApplicationEvent toFailureEvent(String errorDetail) {
    return SessionActivityEvent.failure(
        ExecutionActivityType.SESSION_STARTED,
        dayId.value().toString(),
        ExecutionActivityType.SESSION_STARTED.getDefaultDescription(),
        errorDetail);
  }
}
