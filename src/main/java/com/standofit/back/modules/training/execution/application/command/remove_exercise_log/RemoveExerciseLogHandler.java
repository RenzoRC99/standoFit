package com.standofit.back.modules.training.execution.application.command.remove_exercise_log;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class RemoveExerciseLogHandler implements CommandHandler<RemoveExerciseLogCommand, Void> {

    private final SessionRepository repository;

    public RemoveExerciseLogHandler(SessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<RemoveExerciseLogCommand> commandType() {
        return RemoveExerciseLogCommand.class;
    }

    @Override
    public Void handle(RemoveExerciseLogCommand command) {
        Session session = repository.findById(command.sessionId());
        repository.save(session.removeLog(command.logId()));
        return null;
    }
}