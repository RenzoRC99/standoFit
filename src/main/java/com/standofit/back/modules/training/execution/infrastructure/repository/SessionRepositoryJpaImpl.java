package com.standofit.back.modules.training.execution.infrastructure.repository;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.SessionInfrastructureErrors;
import com.standofit.back.modules.training.execution.infrastructure.SessionInfrastructureException;
import com.standofit.back.modules.training.execution.infrastructure.SessionNotFoundException;
import com.standofit.back.modules.training.execution.infrastructure.mapper.SessionMapper;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.List;
import java.util.Optional;
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
  public Optional<Session> findById(SessionId id) {
    try {
      return jpaRepository.findById(id.value()).map(mapper::toDomain);
    } catch (DataAccessException e) {
      throw new SessionInfrastructureException(
          SessionInfrastructureErrors.FIND_FAILED.getMessage(), e);
    }
  }

  @Override
  public Session getById(SessionId id) {
    return findById(id).orElseThrow(() -> new SessionNotFoundException(id.value().toString()));
  }

  @Override
  public List<Session> findAll() {
    try {
      return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
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
