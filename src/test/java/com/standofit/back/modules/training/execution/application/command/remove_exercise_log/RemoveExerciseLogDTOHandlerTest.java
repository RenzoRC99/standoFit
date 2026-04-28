package com.standofit.back.modules.training.execution.application.command.remove_exercise_log;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveExerciseLogDTOHandlerTest {

    @Mock
    private SessionRepository repository;

    private RemoveExerciseLogHandler handler;

    @BeforeEach
    void setUp() {
        handler = new RemoveExerciseLogHandler(repository);
    }

    @Test
    void should_call_repository_to_remove_log() {
        SessionId sessionId = new SessionId(UUID.randomUUID());
        SessionDayId dayId = new SessionDayId(UUID.randomUUID());
        ExerciseLogId logId = new ExerciseLogId(UUID.randomUUID());
        RemoveExerciseLogCommand command = new RemoveExerciseLogCommand(sessionId, logId);

        Session session = Session.create(sessionId, dayId);
        when(repository.findById(sessionId)).thenReturn(session);
        when(repository.save(any(Session.class))).thenReturn(session);

        handler.handle(command);

        verify(repository, times(1)).findById(sessionId);
        verify(repository, times(1)).save(any(Session.class));
    }
}
