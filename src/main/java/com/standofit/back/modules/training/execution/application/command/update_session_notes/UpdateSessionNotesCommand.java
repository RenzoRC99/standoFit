package com.standofit.back.modules.training.execution.application.command.update_session_notes;

import com.standofit.back.modules.training.execution.domain.vo.WorkoutSessionNotes;
import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.EventfulCommand;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;

public record UpdateSessionNotesCommand(SessionId sessionId, WorkoutSessionNotes notes)
    implements Command<Void>, EventfulCommand {

  public ApplicationEvent toSuccessEvent() {
    return SessionActivityEvent.success(
        ExecutionActivityType.SESSION_NOTES_UPDATED,
        sessionId.value().toString(),
        ExecutionActivityType.SESSION_NOTES_UPDATED.getDefaultDescription());
  }

  public ApplicationEvent toFailureEvent(String errorDetail) {
    return SessionActivityEvent.failure(
        ExecutionActivityType.SESSION_NOTES_UPDATED,
        sessionId.value().toString(),
        ExecutionActivityType.SESSION_NOTES_UPDATED.getDefaultDescription(),
        errorDetail);
  }
}
