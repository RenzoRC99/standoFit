package com.standofit.back.modules.training.execution.application.command.start_session;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;

public record StartSessionCommand(
        SessionDayId dayId
) implements Command<SessionId> {
}