package com.standofit.back.modules.training.planning.presentation;

import com.standofit.back.api.planning.dto.SearchWorkoutsRequest;
import com.standofit.back.modules.training.planning.application.query.search_workouts.SearchWorkoutsQuery;
import java.util.List;
import java.util.Map;

public class PlanningQueryMapper {

  public static SearchWorkoutsQuery all() {
    return new SearchWorkoutsQuery("createdAt", "DESC", 0, 10, List.of());
  }

  public static SearchWorkoutsQuery fromRequest(SearchWorkoutsRequest request) {
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

    return new SearchWorkoutsQuery(
        request.getOrderBy(),
        request.getOrder() != null ? request.getOrder().getValue() : null,
        request.getPage() != null ? request.getPage() : 0,
        request.getPageSize() != null ? request.getPageSize() : 10,
        filters);
  }
}
