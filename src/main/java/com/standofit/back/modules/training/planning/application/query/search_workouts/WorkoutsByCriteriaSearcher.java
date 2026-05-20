package com.standofit.back.modules.training.planning.application.query.search_workouts;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.application.service.ExerciseEnrichmentService;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.Filters;
import com.standofit.back.shared.domain.criteria.Order;
import com.standofit.back.shared.domain.criteria.PagedResult;
import org.springframework.stereotype.Service;

@Service
public class WorkoutsByCriteriaSearcher extends PlanningUseCase {

  private final ExerciseEnrichmentService enrichmentService;

  public WorkoutsByCriteriaSearcher(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus,
      ExerciseEnrichmentService enrichmentService) {
    super(repository, mapper, applicationEventBus);
    this.enrichmentService = enrichmentService;
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

      var workouts = repository.searchByCriteria(criteria);
      var exerciseMaps = enrichmentService.loadExerciseData(workouts);
      var enriched = workouts.stream()
          .map(w -> mapper.toDto(w, exerciseMaps.names(), exerciseMaps.muscleGroups()))
          .toList();
      long total = repository.countByCriteria(criteria);

      PagedResult<WorkoutDto> result = PagedResult.of(enriched, total, page, pageSize);
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
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
