package com.standofit.back.modules.training.planning.application.query.search_workouts;

import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.Filter;
import com.standofit.back.shared.domain.criteria.Order;
import com.standofit.back.shared.domain.criteria.PagedResult;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SearchWorkoutsHandler implements QueryHandler<SearchWorkoutsQuery, PagedResult<WorkoutDto>> {

  private final WorkoutRepository repository;
  private final WorkoutDtoMapper mapper;

  public SearchWorkoutsHandler(WorkoutRepository repository, WorkoutDtoMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Class<SearchWorkoutsQuery> queryType() {
    return SearchWorkoutsQuery.class;
  }

  @Override
  public PagedResult<WorkoutDto> handle(SearchWorkoutsQuery query) {
    Criteria criteria = toCriteria(query);

    List<WorkoutDto> workouts = repository.searchByCriteria(criteria).stream()
        .map(mapper::toDto)
        .toList();

    long total = repository.countByCriteria(criteria);

    return PagedResult.of(workouts, total, query.page(), query.pageSize());
  }

  private Criteria toCriteria(SearchWorkoutsQuery query) {
    var builder = Criteria.builder();

    String orderBy = query.orderBy() != null ? query.orderBy() : "createdAt";
    Order order = "ASC".equalsIgnoreCase(query.order()) ? Order.ASC : Order.DESC;
    builder.order(orderBy, order);

    builder.page(query.page(), query.pageSize());

    if (query.filters() != null && !query.filters().isEmpty()) {
      List<Filter> filters = query.filters().stream()
          .map(this::parseFilter)
          .collect(Collectors.toList());
      builder.filters(filters);
    }

    return builder.build();
  }

  private Filter parseFilter(String filterStr) {
    String[] parts = filterStr.split(":");
    if (parts.length < 3) {
      return Filter.equal(parts[0], parts.length == 2 ? parts[1] : "");
    }
    String field = parts[0];
    String operator = parts[1];
    String value = parts[2];
    return switch (operator) {
      case "LIKE" -> Filter.like(field, value);
      case "GT" -> Filter.gt(field, value);
      case "GTE" -> Filter.gte(field, value);
      case "LT" -> Filter.lt(field, value);
      case "LTE" -> Filter.lte(field, value);
      case "IS_NULL" -> Filter.isNull(field);
      case "IS_NOT_NULL" -> Filter.isNotNull(field);
      default -> Filter.equal(field, value);
    };
  }
}