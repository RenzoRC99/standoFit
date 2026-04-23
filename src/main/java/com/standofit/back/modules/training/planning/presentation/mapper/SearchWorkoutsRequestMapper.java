package com.standofit.back.modules.training.planning.presentation.mapper;

import com.standofit.back.modules.training.planning.presentation.dto.SearchWorkoutsRequest;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.Filter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SearchWorkoutsRequestMapper {

    public Criteria toCriteria(SearchWorkoutsRequest request) {
        Criteria.CriteriaBuilder builder = Criteria.builder();

        if (request.filters() != null && !request.filters().isEmpty()) {
            var filters = request.filters().stream()
                    .map(f -> new Filter(f.field(), f.operator(), f.value()))
                    .collect(Collectors.toList());
            builder.filters(filters);
        }

        if (request.orderBy() != null && !request.orderBy().isBlank()) {
            builder.order(request.orderBy(), request.order());
        }

        builder.page(request.page(), request.pageSize());

        return builder.build();
    }
}
