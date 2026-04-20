package com.standofit.back.training.planning.infrastructure.repository;

import com.standofit.back.training.planning.domain.entity.Workout;
import com.standofit.back.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.training.planning.infrastructure.WorkoutInfrastructureErrors;
import com.standofit.back.training.planning.infrastructure.WorkoutInfrastructureException;
import com.standofit.back.training.planning.infrastructure.entity.WorkoutJpaEntity;
import com.standofit.back.training.planning.infrastructure.mapper.WorkoutMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class WorkoutRepositoryJpa implements WorkoutRepository {

    private final WorkoutJpaRepository jpaRepository;
    private final WorkoutMapper mapper;

    public WorkoutRepositoryJpa(WorkoutJpaRepository jpaRepository, WorkoutMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Workout save(Workout workout) {
        try {
            WorkoutJpaEntity entity = mapper.toEntity(workout);
            WorkoutJpaEntity saved = jpaRepository.save(entity);
            return mapper.toDomain(saved);
        } catch (Exception e) {
            throw new WorkoutInfrastructureException(WorkoutInfrastructureErrors.SAVE_FAILED.getMessage());
        }
    }

    @Override
    public Optional<Workout> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        try {
            if (!jpaRepository.existsById(id)) {
                throw new WorkoutInfrastructureException(WorkoutInfrastructureErrors.NOT_FOUND.getMessage());
            }
            jpaRepository.deleteById(id);
        } catch (WorkoutInfrastructureException e) {
            throw e;
        } catch (Exception e) {
            throw new WorkoutInfrastructureException(WorkoutInfrastructureErrors.DELETE_FAILED.getMessage());
        }
    }
}
