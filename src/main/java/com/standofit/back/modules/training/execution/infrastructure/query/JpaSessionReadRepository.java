package com.standofit.back.modules.training.execution.infrastructure.query;

import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.query.SessionReadRepository;
import com.standofit.back.modules.training.execution.infrastructure.entity.readview.SessionReadViewJpaEntity;
import com.standofit.back.modules.training.execution.infrastructure.mapper.JpaSessionReadMapper;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.PagedResult;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class JpaSessionReadRepository implements SessionReadRepository {

  private final SessionReadViewJpaRepository jpaRepository;
  private final JpaSessionReadMapper mapper;

  public JpaSessionReadRepository(
      SessionReadViewJpaRepository jpaRepository, JpaSessionReadMapper mapper) {
    this.jpaRepository = jpaRepository;
    this.mapper = mapper;
  }

  @Override
  public PagedResult<SessionDto> searchByCriteria(Criteria criteria) {
    var spec = new SessionSpecification(criteria);
    var pageable =
        PageRequest.of(
            criteria.pageInfo().page(),
            criteria.pageInfo().pageSize(),
            Sort.by(toDirection(criteria.order()), criteria.orderBy()));
    Page<SessionReadViewJpaEntity> page = jpaRepository.findAll(spec, pageable);
    List<SessionDto> dtos = page.getContent().stream().map(mapper::toDto).toList();
    return PagedResult.of(
        dtos, page.getTotalElements(), criteria.pageInfo().page(), criteria.pageInfo().pageSize());
  }

  private Sort.Direction toDirection(com.standofit.back.shared.domain.criteria.Order order) {
    return order == com.standofit.back.shared.domain.criteria.Order.ASC
        ? Sort.Direction.ASC
        : Sort.Direction.DESC;
  }
}
