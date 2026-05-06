package com.standofit.back.modules.training.planning.application.query.search_workouts;

import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import com.standofit.back.shared.domain.criteria.Filters;
import com.standofit.back.shared.domain.criteria.Order;
import com.standofit.back.shared.domain.criteria.PagedResult;

import org.springframework.stereotype.Component;

@Component
public class SearchWorkoutsHandler implements QueryHandler<SearchWorkoutsQuery, PagedResult<WorkoutDto>> {

  private final WorkoutsByCriteriaSearcher searcher;

  public SearchWorkoutsHandler(WorkoutsByCriteriaSearcher searcher) {
    this.searcher = searcher;
  }

  @Override
  public Class<SearchWorkoutsQuery> queryType() {
    return SearchWorkoutsQuery.class;
  }

  @Override
  public PagedResult<WorkoutDto> handle(SearchWorkoutsQuery query) {
    Filters filters = Filters.fromValues(query.filters());
    Order order = Order.fromValues(query.orderBy(), query.order());

    return searcher.search(filters, order, query.page(), query.pageSize());
  }
}