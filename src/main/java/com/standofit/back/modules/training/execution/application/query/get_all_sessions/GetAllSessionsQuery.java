package com.standofit.back.modules.training.execution.application.query.get_all_sessions;

import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import com.standofit.back.shared.domain.bus.query.Query;

public record GetAllSessionsQuery() implements Query<SessionListDto> {}
