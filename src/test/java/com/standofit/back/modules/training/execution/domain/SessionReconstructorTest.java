package com.standofit.back.modules.training.execution.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.event.SessionDeleted;
import com.standofit.back.modules.training.execution.domain.event.SessionStarted;
import com.standofit.back.modules.training.execution.domain.vo.WorkoutSessionStatus;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Session Reconstructor Tests")
class SessionReconstructorTest {

  @Test
  @DisplayName("should replay a session with multiple events")
  void shouldReplaySession() {
    UUID id = UUID.randomUUID();
    UUID dayId = UUID.randomUUID();
    var events =
        List.<com.standofit.back.shared.domain.bus.event.DomainEvent>of(
            new SessionStarted(new SessionId(id), new SessionDayId(dayId)));

    Session session = SessionReconstructor.replay(events);

    assertEquals(new SessionId(id), session.getId());
    assertEquals(new SessionDayId(dayId), session.getDayId());
    assertEquals(WorkoutSessionStatus.IN_PROGRESS, session.getStatus());
  }

  @Test
  @DisplayName(
      "should throw SessionDomainException with SESSION_NOT_FOUND when last event is SessionDeleted")
  void shouldThrowWhenLastEventIsSessionDeleted() {
    UUID id = UUID.randomUUID();
    UUID dayId = UUID.randomUUID();
    var events =
        List.<com.standofit.back.shared.domain.bus.event.DomainEvent>of(
            new SessionStarted(new SessionId(id), new SessionDayId(dayId)),
            new SessionDeleted(new SessionId(id)));

    SessionDomainException ex =
        assertThrows(SessionDomainException.class, () -> SessionReconstructor.replay(events));
    assertTrue(ex.getMessage().contains("Session not found"));
  }

  @Test
  @DisplayName("should throw when events list is empty")
  void shouldThrowOnEmptyEvents() {
    assertThrows(SessionDomainException.class, () -> SessionReconstructor.replay(List.of()));
  }
}
