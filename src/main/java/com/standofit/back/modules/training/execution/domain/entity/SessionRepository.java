package com.standofit.back.modules.training.execution.domain.entity;

import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

import java.util.List;

public interface SessionRepository {

    Session save(Session session);

    Session findById(SessionId id);

    void delete(SessionId id);

    List<Session> getAll();

}
