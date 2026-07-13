package com.standofit.back.modules.training.execution.infrastructure.query;

import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import com.standofit.back.modules.training.execution.application.query.SessionReadRepository;
import com.standofit.back.modules.training.execution.infrastructure.entity.SessionJpaEntity;
import com.standofit.back.modules.training.execution.infrastructure.mapper.JpaSessionReadMapper;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class JpaSessionReadRepository implements SessionReadRepository {

  private final EntityManager em;
  private final JpaSessionReadMapper mapper;

  public JpaSessionReadRepository(EntityManager em, JpaSessionReadMapper mapper) {
    this.em = em;
    this.mapper = mapper;
  }

  @Override
  public SessionListDto findAllAsDtos() {
    var entities =
        em.createQuery(
                "SELECT DISTINCT s FROM SessionJpaEntity s LEFT JOIN FETCH s.logs",
                SessionJpaEntity.class)
            .getResultList();
    return new SessionListDto(entities.stream().map(mapper::toDto).toList());
  }

  @Override
  public Optional<SessionDto> findDtoById(UUID id) {
    var list =
        em.createQuery(
                "SELECT s FROM SessionJpaEntity s LEFT JOIN FETCH s.logs WHERE s.id = :id",
                SessionJpaEntity.class)
            .setParameter("id", id)
            .getResultList();
    return list.stream().findFirst().map(mapper::toDto);
  }
}
