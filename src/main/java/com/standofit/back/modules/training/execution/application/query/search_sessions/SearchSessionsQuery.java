package com.standofit.back.modules.training.execution.application.query.search_sessions;

import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.criteria.PagedResult;
import java.util.List;
import java.util.Map;

public record SearchSessionsQuery(
    String orderBy, String order, int page, int pageSize, List<Map<String, String>> filters)
    implements Query<PagedResult<SessionDto>> {

  public static SearchSessionsQuery all() {
    return new SearchSessionsQuery("createdAt", "DESC", 0, 20, List.of());
  }

  public static SearchSessionsQuery byId(
      com.standofit.back.shared.domain.valueobjects.ids.SessionId id) {
    return new SearchSessionsQuery(
        "createdAt",
        "DESC",
        0,
        1,
        List.of(Map.of("field", "id", "operator", "=", "value", id.value().toString())));
  }
}
