package com.standofit.back.modules.training.planning.application.query.search_workouts;

import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.PagedResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SearchWorkoutsHandler
    implements QueryHandler<SearchWorkoutsQuery, PagedResult<WorkoutDto>> {

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
    Criteria criteria = query.criteria();

    List<WorkoutDto> workouts =
        repository.searchByCriteria(criteria).stream().map(mapper::toDto).toList();

    long total = repository.countByCriteria(criteria);

    return PagedResult.of(
        workouts, total, criteria.pageInfo().page(), criteria.pageInfo().pageSize());
  }
}
