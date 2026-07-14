package com.standofit.back.modules.training.planning.infrastructure.repository;

import com.standofit.back.modules.training.planning.infrastructure.entity.WorkoutDayJpaEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutDayJpaRepository extends JpaRepository<WorkoutDayJpaEntity, UUID> {}
