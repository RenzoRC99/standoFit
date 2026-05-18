package com.standofit.back.modules.training.execution.infrastructure.repository;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.SessionInfrastructureErrors;
import com.standofit.back.modules.training.execution.infrastructure.SessionInfrastructureException;
import com.standofit.back.modules.training.execution.infrastructure.mapper.SessionMapper;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepositoryJpaImpl implements SessionRepository {

  private final SessionJpaRepository jpaRepository;
  private final SessionMapper mapper;

  public SessionRepositoryJpaImpl(SessionJpaRepository jpaRepository, SessionMapper mapper) {
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
  public Session findById(SessionId id) {
    try {
      return jpaRepository.findById(id.value()).map(mapper::toDomain).orElse(null);
    } catch (DataAccessException e) {
      throw new SessionInfrastructureException(
          SessionInfrastructureErrors.FIND_FAILED.getMessage(), e);
    }
  }

  @Override
  public void delete(SessionId id) {
    try {
      jpaRepository.deleteById(id.value());
      jpaRepository.flush();
    } catch (Exception e) {
      throw new SessionInfrastructureException(
          SessionInfrastructureErrors.DELETE_FAILED.getMessage(), e);
    }
  }

  @Override
  public List<Session> getAll() {
    return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
  }
}
