package com.standofit.back.modules.training.planning.infrastructure.repository;

import com.standofit.back.modules.training.planning.infrastructure.entity.WorkoutJpaEntity;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.Filter;
import com.standofit.back.shared.domain.criteria.FilterOperator;
import com.standofit.back.shared.domain.criteria.Order;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class WorkoutSpecification implements Specification<WorkoutJpaEntity> {

  private final Criteria criteria;

  public WorkoutSpecification(Criteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public Predicate toPredicate(
      Root<WorkoutJpaEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    if (criteria.hasFilters()) {
      for (Filter filter : criteria.filters()) {
        predicates.add(toPredicate(root, cb, filter));
      }
    }

    if (criteria.hasOrder()) {
      var order =
          criteria.order() == Order.DESC
              ? cb.desc(root.get(criteria.orderBy()))
              : cb.asc(root.get(criteria.orderBy()));
      query.orderBy(order);
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }

  private Predicate toPredicate(Root<WorkoutJpaEntity> root, CriteriaBuilder cb, Filter filter) {
    String field = filter.field();
    Object value = filter.value();
    FilterOperator operator = filter.operator();

    if (value == null
        && operator != FilterOperator.IS_NULL
        && operator != FilterOperator.IS_NOT_NULL) {
      return cb.conjunction();
    }

    return switch (operator) {
      case EQUAL -> cb.equal(root.get(field), value);
      case NOT_EQUAL -> cb.notEqual(root.get(field), value);
      case GT -> cb.greaterThan(root.get(field), (Comparable) value);
      case GTE -> cb.greaterThanOrEqualTo(root.get(field), (Comparable) value);
      case LT -> cb.lessThan(root.get(field), (Comparable) value);
      case LTE -> cb.lessThanOrEqualTo(root.get(field), (Comparable) value);
      case LIKE -> cb.like(cb.lower(root.get(field)), "%" + value.toString().toLowerCase() + "%");
      case IN -> root.get(field).in(value);
      case IS_NULL -> cb.isNull(root.get(field));
      case IS_NOT_NULL -> cb.isNotNull(root.get(field));
    };
  }
}
