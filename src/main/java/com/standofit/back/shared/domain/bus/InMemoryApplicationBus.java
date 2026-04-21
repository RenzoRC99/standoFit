package com.standofit.back.shared.domain.bus;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import java.util.HashMap;
import java.util.Map;

/**
 * In-memory implementation of the ApplicationBus that handles both Queries and Commands.
 *
 * This implementation maintains two separate handler registries:
 * - queryHandlers: for read operations
 * - commandHandlers: for write operations
 *
 * @author standofit
 * @version 1.0
 */
public class InMemoryApplicationBus implements ApplicationBus {

    private final Map<Class<? extends Query>, QueryHandler<?, ?>> queryHandlers = new HashMap<>();
    private final Map<Class<? extends Command<?>>, CommandHandler<?, ?>> commandHandlers = new HashMap<>();

    public void registerQueryHandler(Class<? extends Query> queryClass, QueryHandler<?, ?> handler) {
        queryHandlers.put(queryClass, handler);
    }

    public void registerCommandHandler(Class<? extends Command<?>> commandClass, CommandHandler<?, ?> handler) {
        commandHandlers.put(commandClass, handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R ask(Query query) {
        QueryHandler<Query, R> handler = (QueryHandler<Query, R>) queryHandlers.get(query.getClass());
        if (handler == null) {
            throw new IllegalStateException("No handler registered for query: " + query.getClass().getSimpleName());
        }
        return handler.handle(query);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R execute(Command<R> command) {
        CommandHandler<Command<R>, R> handler = (CommandHandler<Command<R>, R>) commandHandlers.get(command.getClass());
        if (handler == null) {
            throw new IllegalStateException("No handler registered for command: " + command.getClass().getSimpleName());
        }
        return handler.handle(command);
    }
}