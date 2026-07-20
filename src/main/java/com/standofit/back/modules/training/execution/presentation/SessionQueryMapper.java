package com.standofit.back.modules.training.execution.presentation;

import com.standofit.back.api.execution.dto.SearchSessionsRequest;
import com.standofit.back.modules.training.execution.application.query.search_sessions.SearchSessionsQuery;
import java.util.List;
import java.util.Map;

public class SessionQueryMapper {

  public static SearchSessionsQuery all() {
    return new SearchSessionsQuery("createdAt", "DESC", 0, 20, List.of());
  }

  public static SearchSessionsQuery fromRequest(SearchSessionsRequest request) {
    var filters =
        request.getFilters() != null
            ? request.getFilters().stream()
                .map(
                    f ->
                        Map.of(
                            "field",
                            f.getField(),
                            "operator",
                            f.getOperator(),
                            "value",
                            f.getValue()))
                .toList()
            : List.<Map<String, String>>of();

    return new SearchSessionsQuery(
        request.getOrderBy(),
        request.getOrder() != null ? request.getOrder().getValue() : null,
        request.getPage() != null ? request.getPage() : 0,
        request.getPageSize() != null ? request.getPageSize() : 20,
        filters);
  }
}
