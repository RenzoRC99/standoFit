package com.standofit.back.modules.training.execution.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.standofit.back.modules.training.execution.domain.SessionDomainException;
import com.standofit.back.modules.training.execution.domain.vo.WorkoutSessionStatus;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SessionTest {

  @Test
  void should_create_session_in_progress() {
    SessionId id = new SessionId(UUID.randomUUID());
    SessionDayId dayId = new SessionDayId(UUID.randomUUID());

    Session session = Session.create(id, dayId);

    assertEquals(id, session.getId());
    assertEquals(dayId, session.getDayId());
    assertEquals(WorkoutSessionStatus.IN_PROGRESS, session.getStatus());
    assertTrue(session.getLogs().isEmpty());
    assertNotNull(session.getCreatedAt());
    assertNotNull(session.getUpdatedAt());
  }

  @Test
  void should_finish_session() {
    Session session =
        Session.create(new SessionId(UUID.randomUUID()), new SessionDayId(UUID.randomUUID()));

    Session finished = session.finish();

    assertEquals(WorkoutSessionStatus.COMPLETED, finished.getStatus());
    assertNotNull(finished.getUpdatedAt());
  }

  @Test
  void should_cancel_session() {
    Session session =
        Session.create(new SessionId(UUID.randomUUID()), new SessionDayId(UUID.randomUUID()));

    Session cancelled = session.cancel();

    assertEquals(WorkoutSessionStatus.CANCELLED, cancelled.getStatus());
  }

  @Test
  void should_not_finish_already_finished() {
    Session session =
        Session.create(new SessionId(UUID.randomUUID()), new SessionDayId(UUID.randomUUID()))
            .finish();

    assertThrows(SessionDomainException.class, session::finish);
  }

  @Test
  void should_not_cancel_already_finished() {
    Session session =
        Session.create(new SessionId(UUID.randomUUID()), new SessionDayId(UUID.randomUUID()))
            .finish();

    assertThrows(SessionDomainException.class, session::cancel);
  }

  @Test
  void should_not_cancel_already_cancelled() {
    Session session =
        Session.create(new SessionId(UUID.randomUUID()), new SessionDayId(UUID.randomUUID()))
            .cancel();

    assertThrows(SessionDomainException.class, session::cancel);
  }

  @Test
  void should_not_finish_cancelled_session() {
    Session session =
        Session.create(new SessionId(UUID.randomUUID()), new SessionDayId(UUID.randomUUID()))
            .cancel();

    assertThrows(SessionDomainException.class, session::finish);
  }
}
