package com.standofit.back.modules.training.execution.domain.entity;

import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public interface SessionRepository {

  Session save(Session session);

  Session getById(SessionId id);

  void deleteById(SessionId id);
}
