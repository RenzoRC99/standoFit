package com.standofit.back.training.planning.infrastructure.repository;

import com.standofit.back.training.planning.domain.entity.Workout;
import com.standofit.back.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.training.planning.infrastructure.WorkoutInfrastructureErrors;
import com.standofit.back.training.planning.infrastructure.mapper.DatabaseExceptionMapper;
import com.standofit.back.training.planning.infrastructure.mapper.WorkoutMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class WorkoutRepositoryJpaImpl implements WorkoutRepository {

    private final WorkoutJpaRepository jpaRepository;
    private final WorkoutMapper mapper;
    private final DatabaseExceptionMapper exceptionMapper;

    public WorkoutRepositoryJpaImpl(WorkoutJpaRepository jpaRepository, 
                                     WorkoutMapper mapper,
                                     DatabaseExceptionMapper exceptionMapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.exceptionMapper = exceptionMapper;
    }

    @Override
    public Workout save(Workout workout) {
        try {
            return mapper.toDomain(jpaRepository.saveAndFlush(mapper.toEntity(workout)));
        } catch (DataAccessException e) {
            throw exceptionMapper.map(e, WorkoutInfrastructureErrors.SAVE_FAILED);
        }
    }

    @Override
    public Optional<Workout> findById(UUID id) {
        try {
            return jpaRepository.findById(id).map(mapper::toDomain);
        } catch (DataAccessException e) {
            throw exceptionMapper.map(e, WorkoutInfrastructureErrors.FIND_FAILED);
        }
    }

    @Override
    public void deleteById(UUID id) {
        try {
            jpaRepository.deleteById(id);
            jpaRepository.flush();
        } catch (DataAccessException e) {
            throw exceptionMapper.map(e, WorkoutInfrastructureErrors.DELETE_FAILED);
        }
    }
}