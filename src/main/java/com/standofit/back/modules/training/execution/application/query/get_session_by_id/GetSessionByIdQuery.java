package com.standofit.back.modules.training.execution.application.query.get_session_by_id;

import com.standofit.back.modules.training.execution.presentation.dto.SessionDto;
import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public record GetSessionByIdQuery(
        SessionId id
) implements Query<SessionDto> {
}