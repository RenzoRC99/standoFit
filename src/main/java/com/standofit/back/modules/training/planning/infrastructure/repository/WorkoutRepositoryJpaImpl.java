package com.standofit.back.modules.training.planning.infrastructure.repository;

import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureErrors;
import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureException;
import com.standofit.back.modules.training.planning.infrastructure.entity.WorkoutJpaEntity;
import com.standofit.back.modules.training.planning.infrastructure.mapper.WorkoutMapper;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class WorkoutRepositoryJpaImpl implements WorkoutRepository {

  private final WorkoutJpaRepository jpaRepository;
  private final WorkoutMapper mapper;

  public WorkoutRepositoryJpaImpl(WorkoutJpaRepository jpaRepository, WorkoutMapper mapper) {
    this.jpaRepository = jpaRepository;
    this.mapper = mapper;
  }

  @Override
  public Workout save(Workout workout) {
    try {
      return mapper.toDomain(jpaRepository.saveAndFlush(mapper.toEntity(workout)));
    } catch (DataAccessException e) {
      throw new WorkoutInfrastructureException(
          WorkoutInfrastructureErrors.SAVE_FAILED.getMessage(), e);
    }
  }

  @Override
  public Optional<Workout> findById(WorkoutId id) {
    try {
      return jpaRepository.findById(id.value()).map(mapper::toDomain);
    } catch (DataAccessException e) {
      throw new WorkoutInfrastructureException(
          WorkoutInfrastructureErrors.FIND_FAILED.getMessage(), e);
    }
  }

  @Override
  public Workout getById(WorkoutId id) {
    return findById(id)
        .orElseThrow(
            () ->
                new WorkoutInfrastructureException(
                    WorkoutInfrastructureErrors.WORKOUT_NOT_FOUND.getMessage(id.value())));
  }

  @Override
  public List<Workout> findAll() {
    try {
      return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    } catch (DataAccessException e) {
      throw new WorkoutInfrastructureException(
          WorkoutInfrastructureErrors.FIND_FAILED.getMessage(), e);
    }
  }

  @Override
  public List<Workout> searchByCriteria(Criteria criteria) {
    try {
      WorkoutSpecification spec = new WorkoutSpecification(criteria);
      Pageable pageable =
          PageRequest.of(criteria.pageInfo().page(), criteria.pageInfo().pageSize());
      Page<WorkoutJpaEntity> page = jpaRepository.findAll(spec, pageable);
      return page.getContent().stream().map(mapper::toDomain).toList();
    } catch (DataAccessException e) {
      throw new WorkoutInfrastructureException(
          WorkoutInfrastructureErrors.FIND_FAILED.getMessage(), e);
    }
  }

  @Override
  public long countByCriteria(Criteria criteria) {
    try {
      WorkoutSpecification spec = new WorkoutSpecification(criteria);
      return jpaRepository.count(spec);
    } catch (DataAccessException e) {
      throw new WorkoutInfrastructureException(
          WorkoutInfrastructureErrors.FIND_FAILED.getMessage(), e);
    }
  }

  @Override
  public void deleteById(WorkoutId id) {
    try {
      jpaRepository.deleteById(id.value());
      jpaRepository.flush();
    } catch (DataAccessException e) {
      throw new WorkoutInfrastructureException(
          WorkoutInfrastructureErrors.DELETE_FAILED.getMessage(), e);
    }
  }
}
