package com.standofit.back.modules.training.execution.infrastructure.repository;

import com.standofit.back.modules.training.execution.infrastructure.entity.SessionJpaEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionJpaRepository extends JpaRepository<SessionJpaEntity, UUID> {}
