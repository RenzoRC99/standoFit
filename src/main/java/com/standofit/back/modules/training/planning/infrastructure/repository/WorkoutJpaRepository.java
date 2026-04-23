package com.standofit.back.modules.training.planning.infrastructure.repository;

import com.standofit.back.modules.training.planning.infrastructure.entity.WorkoutJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkoutJpaRepository extends JpaRepository<WorkoutJpaEntity, UUID>, JpaSpecificationExecutor<WorkoutJpaEntity> {
    List<WorkoutJpaEntity> findByNameContainingIgnoreCase(String name);
}
