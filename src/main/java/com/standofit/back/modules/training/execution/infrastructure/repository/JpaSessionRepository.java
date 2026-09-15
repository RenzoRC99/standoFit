package com.standofit.back.modules.training.execution.infrastructure.repository;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.SessionInfrastructureErrors;
import com.standofit.back.modules.training.execution.infrastructure.SessionInfrastructureException;
import com.standofit.back.modules.training.execution.infrastructure.SessionNotFoundException;
import com.standofit.back.modules.training.execution.infrastructure.mapper.JpaSessionMapper;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class JpaSessionRepository implements SessionRepository {

  private final SessionJpaRepository jpaRepository;
  private final JpaSessionMapper mapper;

  public JpaSessionRepository(SessionJpaRepository jpaRepository, JpaSessionMapper mapper) {
    this.jpaRepository = jpaRepository;
    this.mapper = mapper;
  }

  @Override
  public Session save(Session session) {
    try {
      return mapper.toDomain(jpaRepository.saveAndFlush(mapper.toEntity(session)));
    } catch (DataAccessException e) {
      throw new SessionInfrastructureException(
          SessionInfrastructureErrors.SAVE_FAILED.getMessage(), e);
    }
  }

  @Override
  public Session getById(SessionId id) {
    try {
      return jpaRepository
          .findById(id.value())
          .map(mapper::toDomain)
          .orElseThrow(() -> new SessionNotFoundException(id.value().toString()));
    } catch (DataAccessException e) {
      throw new SessionInfrastructureException(
          SessionInfrastructureErrors.FIND_FAILED.getMessage(), e);
    }
  }

  @Override
  public void deleteById(SessionId id) {
    try {
      jpaRepository.deleteById(id.value());
      jpaRepository.flush();
    } catch (DataAccessException e) {
      throw new SessionInfrastructureException(
          SessionInfrastructureErrors.DELETE_FAILED.getMessage(), e);
    }
  }
}
