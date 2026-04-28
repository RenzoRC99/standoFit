package com.standofit.back.modules.training.execution.application.command.delete_session;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public record DeleteSessionCommand(SessionId sessionId) implements Command<Void> {
}