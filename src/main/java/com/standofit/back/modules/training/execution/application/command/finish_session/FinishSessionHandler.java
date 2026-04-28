package com.standofit.back.modules.training.execution.application.command.finish_session;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class FinishSessionHandler implements CommandHandler<FinishSessionCommand, Void> {

    private final SessionRepository repository;

    public FinishSessionHandler(SessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<FinishSessionCommand> commandType() {
        return FinishSessionCommand.class;
    }

    @Override
    public Void handle(FinishSessionCommand command) {
        Session session = repository.findById(command.sessionId());
        repository.save(session.finish());
        return null;
    }
}