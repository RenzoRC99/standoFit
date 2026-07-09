package com.standofit.back.modules.training.execution.domain.entity;

import com.standofit.back.modules.training.execution.domain.vo.SessionCreatedAt;
import com.standofit.back.modules.training.execution.domain.vo.SessionUpdatedAt;
import com.standofit.back.modules.training.execution.domain.vo.WorkoutSessionNotes;
import com.standofit.back.modules.training.execution.domain.vo.WorkoutSessionStatus;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.List;
import java.util.UUID;

public final class SessionMother {

  private SessionMother() {}

  public static SessionId aSessionId() {
    return new SessionId(UUID.randomUUID());
  }

  public static SessionDayId aSessionDayId() {
    return new SessionDayId(UUID.randomUUID());
  }

  public static Session aSession() {
    return Session.create(aSessionId(), aSessionDayId());
  }

  public static Session aSessionWithId(SessionId id) {
    return Session.create(id, aSessionDayId());
  }

  public static Session aSessionWithDayId(SessionDayId dayId) {
    return Session.create(aSessionId(), dayId);
  }

  public static Session aSessionInProgress() {
    return Session.create(aSessionId(), aSessionDayId());
  }

  public static Session aSessionCompleted() {
    return Session.create(aSessionId(), aSessionDayId()).finish();
  }

  public static Session aSessionCancelled() {
    return Session.create(aSessionId(), aSessionDayId()).cancel();
  }

  public static Session aSessionWithLogs(List<ExerciseLog> logs) {
    Session session = Session.create(aSessionId(), aSessionDayId());
    for (ExerciseLog log : logs) {
      session = session.addLog(log);
    }
    return session;
  }

  public static Session aSessionWithNotes(String notes) {
    Session session = Session.create(aSessionId(), aSessionDayId());
    return session.changeNotes(new WorkoutSessionNotes(notes));
  }

  public static Session copy(
      SessionId id,
      SessionDayId dayId,
      WorkoutSessionStatus status,
      List<ExerciseLog> logs,
      WorkoutSessionNotes notes,
      SessionCreatedAt createdAt,
      SessionUpdatedAt updatedAt) {
    return Session.copy(id, dayId, status, logs, notes, createdAt, updatedAt);
  }
}
