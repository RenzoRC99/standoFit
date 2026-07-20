package com.standofit.back.modules.training.planning.infrastructure.repository;

import com.standofit.back.modules.training.planning.infrastructure.entity.WorkoutJpaEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutJpaRepository
    extends JpaRepository<WorkoutJpaEntity, UUID>, JpaSpecificationExecutor<WorkoutJpaEntity> {

  @Query("SELECT w FROM WorkoutJpaEntity w JOIN w.days d WHERE d.id = :dayId")
  Optional<WorkoutJpaEntity> findByDayId(@Param("dayId") UUID dayId);
}
