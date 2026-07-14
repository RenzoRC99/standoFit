package com.standofit.back.modules.training.planning.application.query.search_workouts;

import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.query.WorkoutReadRepository;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.Filters;
import com.standofit.back.shared.domain.criteria.Order;
import com.standofit.back.shared.domain.criteria.PagedResult;
import org.springframework.stereotype.Component;

@Component
public class SearchWorkoutsHandler
    implements QueryHandler<SearchWorkoutsQuery, PagedResult<WorkoutDto>> {

  private final WorkoutReadRepository readRepository;

  public SearchWorkoutsHandler(WorkoutReadRepository readRepository) {
    this.readRepository = readRepository;
  }

  @Override
  public Class<SearchWorkoutsQuery> queryType() {
    return SearchWorkoutsQuery.class;
  }

  @Override
  public PagedResult<WorkoutDto> handle(SearchWorkoutsQuery query) {
    Filters filters = Filters.fromValues(query.filters());
    Order order = Order.fromValues(query.orderBy(), query.order());

    Criteria criteria =
        Criteria.fromValues(
            order != null ? null : "createdAt",
            order != null ? order.name() : "DESC",
            query.page(),
            query.pageSize(),
            filters.items());

    return readRepository.search(criteria);
  }
}
