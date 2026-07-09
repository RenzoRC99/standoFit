package com.standofit.back.modules.training.execution.application.command.cancel_session;

import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.EventfulCommand;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public record CancelSessionCommand(SessionId sessionId) implements Command<Void>, EventfulCommand {

  public ApplicationEvent toSuccessEvent() {
    return SessionActivityEvent.success(
        ExecutionActivityType.SESSION_CANCELLED,
        sessionId.value().toString(),
        ExecutionActivityType.SESSION_CANCELLED.getDefaultDescription());
  }

  public ApplicationEvent toFailureEvent(String errorDetail) {
    return SessionActivityEvent.failure(
        ExecutionActivityType.SESSION_CANCELLED,
        sessionId.value().toString(),
        ExecutionActivityType.SESSION_CANCELLED.getDefaultDescription(),
        errorDetail);
  }
}
