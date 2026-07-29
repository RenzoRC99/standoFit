package com.standofit.back.modules.training.execution.application.command.update_session_notes;

import com.standofit.back.modules.training.execution.domain.vo.WorkoutSessionNotes;
import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public record UpdateSessionNotesCommand(SessionId sessionId, WorkoutSessionNotes notes)
    implements Command<Void> {}
