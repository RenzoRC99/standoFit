package com.standofit.back.training.planning.infrastructure.repository;

import com.standofit.back.training.planning.infrastructure.entity.WorkoutJpaEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutJpaRepository extends JpaRepository<WorkoutJpaEntity, UUID> {}
