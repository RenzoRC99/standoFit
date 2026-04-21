package com.standofit.back.shared.domain.bus.command;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory implementation of the CommandBus.
 *
 * This implementation stores all command handlers in a Map, where the key is the Command class
 * and the value is the corresponding handler. When a command is dispatched, it looks up
 * the handler by the command class and executes it.
 *
 * This is a simple, synchronous implementation suitable for single-server deployments.
 * For distributed systems, consider implementing CommandBus with a message broker.
 *
 * Usage:
 * <pre>
 * // Register handlers (typically done via configuration)
 * InMemoryCommandBus bus = new InMemoryCommandBus();
 * bus.registerHandler(CreateWorkoutCommand.class, new CreateWorkoutHandler(...));
 *
 * // Dispatch command
 * WorkoutDto result = bus.dispatch(new CreateWorkoutCommand(...));
 * </pre>
 *
 * @author standofit
 * @version 1.0
 */
public class InMemoryCommandBus implements CommandBus {

    private final Map<Class<? extends Command<?>>, CommandHandler<?, ?>> handlers = new HashMap<>();

    /**
     * Registers a handler for a specific command class.
     *
     * @param commandClass the class of the command
     * @param handler the handler that will process the command
     */
    public void registerHandler(Class<? extends Command<?>> commandClass, CommandHandler<?, ?> handler) {
        handlers.put(commandClass, handler);
    }

    /**
     * Dispatches the given command to its registered handler and returns the result.
     *
     * @param <R> the type of result expected
     * @param command the command to execute
     * @return the result from the handler
     * @throws IllegalStateException if no handler is registered for the command class
     */
    @Override
    public <R> R dispatch(Command<R> command) {
        @SuppressWarnings("unchecked")
        CommandHandler<Command<R>, R> handler = (CommandHandler<Command<R>, R>) handlers.get(command.getClass());
        if (handler == null) {
            throw new IllegalStateException("No handler registered for: " + command.getClass().getSimpleName());
        }
        return handler.handle(command);
    }
}