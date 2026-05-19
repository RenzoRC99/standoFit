package com.standofit.back.modules.training.execution.application.command.delete_session;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.EventfulCommand;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;

public record DeleteSessionCommand(SessionId sessionId) implements Command<Void>, EventfulCommand {

  public ApplicationEvent toSuccessEvent() {
    return SessionActivityEvent.success(
        ExecutionActivityType.SESSION_DELETED,
        sessionId.value().toString(),
        ExecutionActivityType.SESSION_DELETED.getDefaultDescription());
  }

  public ApplicationEvent toFailureEvent(String errorDetail) {
    return SessionActivityEvent.failure(
        ExecutionActivityType.SESSION_DELETED,
        sessionId.value().toString(),
        ExecutionActivityType.SESSION_DELETED.getDefaultDescription(),
        errorDetail);
  }
}
