package com.standofit.back.modules.training.execution.application.query.get_all_sessions;

import com.standofit.back.api.execution.dto.SessionListDTO;
import com.standofit.back.shared.domain.bus.query.Query;

public record GetAllSessionsQuery() implements Query<SessionListDTO> {}
