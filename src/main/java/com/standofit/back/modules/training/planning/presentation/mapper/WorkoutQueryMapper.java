package com.standofit.back.modules.training.planning.presentation.mapper;

import com.standofit.back.api.planning.dto.SearchWorkoutsRequest;
import com.standofit.back.api.planning.dto.FilterRequest;
import com.standofit.back.modules.training.planning.application.query.get_workout_by_id.GetWorkoutByIdQuery;
import com.standofit.back.modules.training.planning.application.query.search_workouts.SearchWorkoutsQuery;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.Filter;
import com.standofit.back.shared.domain.criteria.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WorkoutQueryMapper {

    public GetWorkoutByIdQuery toGetWorkoutByIdQuery(UUID workoutId) {
        return new GetWorkoutByIdQuery(workoutId);
    }

    public SearchWorkoutsQuery toSearchWorkoutsQuery(SearchWorkoutsRequest request) {
        Criteria.CriteriaBuilder criteriaBuilder = Criteria.builder();

        if (request.getName() != null && !request.getName().isBlank()) {
            criteriaBuilder.filter(Filter.like("name", request.getName()));
        }

        if (request.getFilters() != null) {
            for (FilterRequest filter : request.getFilters()) {
                if (filter.getValue() != null) {
                    criteriaBuilder.filter(toFilter(filter));
                }
            }
        }

        if (request.getOrderBy() != null && !request.getOrderBy().isBlank()) {
            Order order = request.getOrder() != null && request.getOrder() == SearchWorkoutsRequest.OrderEnum.DESC
                    ? Order.DESC
                    : Order.ASC;
            criteriaBuilder.order(request.getOrderBy(), order);
        } else {
            criteriaBuilder.order("createdAt", Order.DESC);
        }

        int page = request.getPage() != null ? request.getPage() : 0;
        int pageSize = request.getPageSize() != null ? request.getPageSize() : 10;
        criteriaBuilder.page(page, pageSize);

        return new SearchWorkoutsQuery(criteriaBuilder.build());
    }

    public Criteria toGetAllWorkoutsCriteria() {
        return Criteria.builder()
                .order("createdAt", Order.DESC)
                .page(0, 10)
                .build();
    }

    private Filter toFilter(FilterRequest request) {
        String operator = request.getOperator();
        if (operator == null) {
            return Filter.equal(request.getField(), request.getValue());
        }
        return switch (operator.toUpperCase()) {
            case "LIKE" -> Filter.like(request.getField(), request.getValue().toString());
            case "EQUAL" -> Filter.equal(request.getField(), request.getValue());
            case "NOT_EQUAL" -> Filter.notEqual(request.getField(), request.getValue());
            case "GT" -> Filter.gt(request.getField(), request.getValue());
            case "LT" -> Filter.lt(request.getField(), request.getValue());
            case "GTE" -> Filter.gte(request.getField(), request.getValue());
            case "LTE" -> Filter.lte(request.getField(), request.getValue());
            default -> Filter.equal(request.getField(), request.getValue());
        };
    }
}