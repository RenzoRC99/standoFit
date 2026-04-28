package com.standofit.back.modules.training.planning.presentation.dto;

import com.standofit.back.shared.domain.criteria.FilterOperator;
import com.standofit.back.shared.domain.criteria.Order;
import java.util.List;

public record SearchWorkoutsRequest(
    List<FilterRequest> filters, String orderBy, Order order, int page, int pageSize) {
  public record FilterRequest(String field, FilterOperator operator, Object value) {}
}
