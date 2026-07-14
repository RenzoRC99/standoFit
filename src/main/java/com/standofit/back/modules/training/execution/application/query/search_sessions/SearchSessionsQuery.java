package com.standofit.back.modules.training.execution.application.query.search_sessions;

import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import com.standofit.back.shared.domain.bus.query.Query;

public record SearchSessionsQuery(
    com.standofit.back.shared.domain.valueobjects.ids.SessionId sessionId,
    String status,
    int page,
    int pageSize)
    implements Query<SessionListDto> {

  public static SearchSessionsQuery all() {
    return new SearchSessionsQuery(null, null, 0, 20);
  }

  public static SearchSessionsQuery byId(
      com.standofit.back.shared.domain.valueobjects.ids.SessionId id) {
    return new SearchSessionsQuery(id, null, 0, 1);
  }

  public static SearchSessionsQuery byStatus(String status) {
    return new SearchSessionsQuery(null, status, 0, 20);
  }
}
