package com.standofit.back.modules.training.execution.application.command.update_exercise_log_reps;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class UpdateExerciseLogRepsHandler implements CommandHandler<UpdateExerciseLogRepsCommand, Void> {

    private final SessionRepository repository;

    public UpdateExerciseLogRepsHandler(SessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<UpdateExerciseLogRepsCommand> commandType() {
        return UpdateExerciseLogRepsCommand.class;
    }

    @Override
    public Void handle(UpdateExerciseLogRepsCommand command) {
        Session session = repository.findById(command.sessionId());
        repository.save(session.updateLogReps(command.logId(), command.reps()));
        return null;
    }
}