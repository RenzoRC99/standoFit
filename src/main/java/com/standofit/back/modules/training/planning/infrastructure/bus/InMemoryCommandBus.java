package com.standofit.back.modules.training.planning.infrastructure.bus;

import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureErrors;
import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureException;
import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.CommandBus;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryCommandBus implements CommandBus {

    private final Map<Class<?>, CommandHandler<?, ?>> handlers;

    public InMemoryCommandBus(List<CommandHandler<?, ?>> handlers) {
        this.handlers = new HashMap<>();

        for (CommandHandler<?, ?> handler : handlers) {
            Class<?> type = handler.commandType();

            if (this.handlers.containsKey(type)) {
                throw new WorkoutInfrastructureException(
                        WorkoutInfrastructureErrors.DUPLICATE_COMMAND_HANDLER.getMessage(type.getSimpleName())
                );
            }

            this.handlers.put(type, handler);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R dispatch(Command<R> command) {

        CommandHandler<Command<R>, R> handler =
                (CommandHandler<Command<R>, R>) handlers.get(command.getClass());

        if (handler == null) {
            throw new WorkoutInfrastructureException(
                    WorkoutInfrastructureErrors.COMMAND_HANDLER_NOT_FOUND.getMessage(command.getClass().getSimpleName())
            );
        }

        return handler.handle(command);
    }
}
