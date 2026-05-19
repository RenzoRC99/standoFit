package com.standofit.back.modules.training.planning.infrastructure.repository;

import com.standofit.back.modules.training.planning.infrastructure.entity.WorkoutJpaEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutJpaRepository
    extends JpaRepository<WorkoutJpaEntity, UUID>, JpaSpecificationExecutor<WorkoutJpaEntity> {
}
