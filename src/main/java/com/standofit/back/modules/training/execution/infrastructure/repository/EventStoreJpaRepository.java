package com.standofit.back.modules.training.execution.infrastructure.repository;

import com.standofit.back.modules.training.execution.infrastructure.entity.EventStoreJpaEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventStoreJpaRepository extends JpaRepository<EventStoreJpaEntity, Long> {

  List<EventStoreJpaEntity> findByAggregateIdOrderByVersionAsc(UUID aggregateId);

  Optional<EventStoreJpaEntity> findTopByAggregateIdOrderByVersionDesc(UUID aggregateId);
}
