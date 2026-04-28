package com.standofit.back.modules.training.execution.application.command.cancel_session;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class CancelSessionHandler implements CommandHandler<CancelSessionCommand, Void> {

    private final SessionRepository repository;

    public CancelSessionHandler(SessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<CancelSessionCommand> commandType() {
        return CancelSessionCommand.class;
    }

    @Override
    public Void handle(CancelSessionCommand command) {
        Session session = repository.findById(command.sessionId());
        repository.save(session.cancel());
        return null;
    }
}