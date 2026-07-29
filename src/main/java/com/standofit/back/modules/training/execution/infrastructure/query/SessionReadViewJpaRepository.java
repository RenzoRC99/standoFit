package com.standofit.back.modules.training.execution.infrastructure.query;

import com.standofit.back.modules.training.execution.infrastructure.entity.readview.SessionReadViewJpaEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionReadViewJpaRepository
    extends JpaRepository<SessionReadViewJpaEntity, UUID>,
        JpaSpecificationExecutor<SessionReadViewJpaEntity> {}
