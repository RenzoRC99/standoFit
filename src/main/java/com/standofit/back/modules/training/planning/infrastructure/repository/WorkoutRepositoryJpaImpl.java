package com.standofit.back.modules.training.planning.infrastructure.repository;

import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureErrors;
import com.standofit.back.modules.training.planning.infrastructure.entity.WorkoutJpaEntity;
import com.standofit.back.modules.training.planning.infrastructure.mapper.DatabaseExceptionMapper;
import com.standofit.back.modules.training.planning.infrastructure.mapper.WorkoutMapper;
import com.standofit.back.shared.domain.criteria.Criteria;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class WorkoutRepositoryJpaImpl implements WorkoutRepository {

    private final WorkoutJpaRepository jpaRepository;
    private final WorkoutMapper mapper;
    private final DatabaseExceptionMapper exceptionMapper;

    public WorkoutRepositoryJpaImpl(
            WorkoutJpaRepository jpaRepository,
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
    public List<Workout> findAll() {
        try {
            return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
        } catch (DataAccessException e) {
            throw exceptionMapper.map(e, WorkoutInfrastructureErrors.FIND_FAILED);
        }
    }

    @Override
    public List<Workout> searchByCriteria(Criteria criteria) {
        try {
            WorkoutSpecification spec = new WorkoutSpecification(criteria);
            Pageable pageable = PageRequest.of(criteria.pageInfo().page(), criteria.pageInfo().pageSize());
            Page<WorkoutJpaEntity> page =
                    jpaRepository.findAll(spec, pageable);
            return page.getContent().stream().map(mapper::toDomain).toList();
        } catch (DataAccessException e) {
            throw exceptionMapper.map(e, WorkoutInfrastructureErrors.FIND_FAILED);
        }
    }

    @Override
    public long countByCriteria(Criteria criteria) {
        try {
            WorkoutSpecification spec = new WorkoutSpecification(criteria);
            return jpaRepository.count(spec);
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
