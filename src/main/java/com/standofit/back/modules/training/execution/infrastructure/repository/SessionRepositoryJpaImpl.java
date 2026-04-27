package com.standofit.back.modules.training.execution.infrastructure.repository;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.SessionInfrastructureErrors;
import com.standofit.back.modules.training.execution.infrastructure.mapper.SessionDatabaseExceptionMapper;
import com.standofit.back.modules.training.execution.infrastructure.mapper.SessionMapper;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepositoryJpaImpl implements SessionRepository {

    private final SessionJpaRepository jpaRepository;
    private final SessionMapper mapper;
    private final SessionDatabaseExceptionMapper exceptionMapper;

    public SessionRepositoryJpaImpl(
            SessionJpaRepository jpaRepository,
            SessionMapper mapper,
            SessionDatabaseExceptionMapper exceptionMapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.exceptionMapper = exceptionMapper;
    }

    @Override
    public Session save(Session session) {
        try {
            return mapper.toDomain(jpaRepository.saveAndFlush(mapper.toEntity(session)));
        } catch (DataAccessException e) {
            throw exceptionMapper.map(e, SessionInfrastructureErrors.SAVE_FAILED);
        }
    }

    @Override
    public Session findById(SessionId id) {
        try {
            return jpaRepository.findById(id.value())
                    .map(mapper::toDomain)
                    .orElse(null);
        } catch (DataAccessException e) {
            throw exceptionMapper.map(e, SessionInfrastructureErrors.FIND_FAILED);
        }
    }

    @Override
    public void delete(SessionId id) {
        try {
            jpaRepository.deleteById(id.value());
            jpaRepository.flush();
        } catch (Exception e) {
            throw exceptionMapper.map(e, SessionInfrastructureErrors.DELETE_FAILED);
        }
    }
}
