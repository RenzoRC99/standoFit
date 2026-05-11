package com.standofit.back.modules.training.planning.application.query.search_workouts;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.Filters;
import com.standofit.back.shared.domain.criteria.Order;
import com.standofit.back.shared.domain.criteria.PagedResult;
import org.springframework.stereotype.Service;

@Service
public class WorkoutsByCriteriaSearcher extends PlanningUseCase {

  public WorkoutsByCriteriaSearcher(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public PagedResult<WorkoutDto> search(Filters filters, Order order, int page, int pageSize) {
    try {
      Criteria criteria =
          Criteria.fromValues(
              order != null ? null : "createdAt",
              order != null ? order.name() : "DESC",
              page,
              pageSize,
              filters.items());

      var workouts = repository.searchByCriteria(criteria).stream().map(mapper::toDto).toList();

      long total = repository.countByCriteria(criteria);

      PagedResult<WorkoutDto> result = PagedResult.of(workouts, total, page, pageSize);
      publishEvent(
          PlanningActivityEvent.success(
              PlanningActivityType.WORKOUTS_SEARCHED,
              null,
              PlanningActivityType.WORKOUTS_SEARCHED.getDefaultDescription()));
      return result;
    } catch (Exception e) {
      publishEvent(
          PlanningActivityEvent.failure(
              PlanningActivityType.WORKOUTS_SEARCHED,
              null,
              PlanningActivityType.WORKOUTS_SEARCHED.getDefaultDescription(),
              e.getMessage()));
      throw e;
    }
  }
}
