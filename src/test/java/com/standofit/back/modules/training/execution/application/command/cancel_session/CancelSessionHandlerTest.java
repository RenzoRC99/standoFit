package com.standofit.back.modules.training.execution.application.command.cancel_session;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.SessionInfrastructureErrors;
import com.standofit.back.modules.training.execution.infrastructure.SessionInfrastructureException;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelSessionHandlerTest {

    @Mock
    private SessionRepository repository;

    private CancelSessionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CancelSessionHandler(repository);
    }

    @Test
    void should_cancel_session() {
        SessionId sessionId = new SessionId(UUID.randomUUID());
        SessionDayId dayId = new SessionDayId(UUID.randomUUID());
        CancelSessionCommand command = new CancelSessionCommand(sessionId);

        Session session = Session.create(sessionId, dayId);
        when(repository.findById(sessionId)).thenReturn(session);
        when(repository.save(any(Session.class))).thenReturn(session.cancel());

        handler.handle(command);

        verify(repository, times(1)).findById(sessionId);
        verify(repository, times(1)).save(any(Session.class));
    }

    @Test
    void should_throw_when_session_not_found() {
        SessionId sessionId = new SessionId(UUID.randomUUID());
        CancelSessionCommand command = new CancelSessionCommand(sessionId);

        when(repository.findById(sessionId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> handler.handle(command));
    }
}