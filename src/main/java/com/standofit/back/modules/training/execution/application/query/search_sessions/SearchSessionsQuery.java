package com.standofit.back.modules.training.execution.application.query.search_sessions;

import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public record SearchSessionsQuery(SessionId sessionId) implements Query<SessionListDto> {

  public static SearchSessionsQuery all() {
    return new SearchSessionsQuery(null);
  }

  public static SearchSessionsQuery byId(SessionId id) {
    return new SearchSessionsQuery(id);
  }
}
