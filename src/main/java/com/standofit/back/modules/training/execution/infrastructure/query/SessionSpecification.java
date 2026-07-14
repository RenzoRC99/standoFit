package com.standofit.back.modules.training.execution.infrastructure.query;

import com.standofit.back.modules.training.execution.infrastructure.entity.readview.SessionReadViewJpaEntity;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.Filter;
import com.standofit.back.shared.domain.criteria.FilterOperator;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class SessionSpecification implements Specification<SessionReadViewJpaEntity> {

  private final Criteria criteria;

  public SessionSpecification(Criteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public Predicate toPredicate(
      Root<SessionReadViewJpaEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    if (criteria.hasFilters()) {
      for (Filter filter : criteria.filters()) {
        predicates.add(toPredicate(root, cb, filter));
      }
    }

    return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
  }

  private Predicate toPredicate(
      Root<SessionReadViewJpaEntity> root, CriteriaBuilder cb, Filter filter) {
    String field = filter.fieldName();
    Object value = filter.rawValue();
    FilterOperator operator = filter.operator();

    if (value == null
        || "".equals(value)
            && operator != FilterOperator.IS_NULL
            && operator != FilterOperator.IS_NOT_NULL) {
      return cb.conjunction();
    }

    Object converted = convertIfNeeded(field, value);

    return switch (operator) {
      case EQUAL -> cb.equal(root.get(field), converted);
      case NOT_EQUAL -> cb.notEqual(root.get(field), converted);
      case GT -> cb.greaterThan(root.get(field), (Comparable) converted);
      case GTE -> cb.greaterThanOrEqualTo(root.get(field), (Comparable) converted);
      case LT -> cb.lessThan(root.get(field), (Comparable) converted);
      case LTE -> cb.lessThanOrEqualTo(root.get(field), (Comparable) converted);
      case LIKE ->
          cb.like(cb.lower(root.get(field)), "%" + converted.toString().toLowerCase() + "%");
      case IN -> root.get(field).in(converted);
      case IS_NULL -> cb.isNull(root.get(field));
      case IS_NOT_NULL -> cb.isNotNull(root.get(field));
    };
  }

  private Object convertIfNeeded(String field, Object value) {
    if ("id".equals(field) || "dayId".equals(field)) {
      return UUID.fromString(value.toString());
    }
    return value;
  }
}
