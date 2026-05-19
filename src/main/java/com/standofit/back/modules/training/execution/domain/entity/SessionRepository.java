package com.standofit.back.modules.training.execution.domain.entity;

import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.List;
import java.util.Optional;

public interface SessionRepository {

  Session save(Session session);

  Optional<Session> findById(SessionId id);

  Session getById(SessionId id);

  List<Session> findAll();

  void deleteById(SessionId id);
}
