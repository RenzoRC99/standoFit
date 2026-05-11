package com.standofit.back.modules.training.execution.application.command.start_session;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import java.util.UUID;

public record StartSessionCommand(SessionDayId dayId) implements Command<UUID> {}
