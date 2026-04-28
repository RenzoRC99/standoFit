package com.standofit.back.modules.training.execution.application.command.start_session;

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
class StartSessionHandlerTest {

    @Mock
    private SessionRepository repository;

    private StartSessionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new StartSessionHandler(repository);
    }

    @Test
    void should_start_session() {
        SessionDayId dayId = new SessionDayId(UUID.randomUUID());
        StartSessionCommand command = new StartSessionCommand(dayId);

        Session savedSession = Session.create(new SessionId(UUID.randomUUID()), dayId);
        when(repository.save(any(Session.class))).thenReturn(savedSession);

        SessionId result = handler.handle(command);

        assertNotNull(result);
        verify(repository, times(1)).save(any(Session.class));
    }

    @Test
    void should_throw_when_repository_fails() {
        SessionDayId dayId = new SessionDayId(UUID.randomUUID());
        StartSessionCommand command = new StartSessionCommand(dayId);

        when(repository.save(any(Session.class)))
            .thenThrow(new SessionInfrastructureException(SessionInfrastructureErrors.SAVE_FAILED.getMessage()));

        assertThrows(SessionInfrastructureException.class, () -> handler.handle(command));
    }
}