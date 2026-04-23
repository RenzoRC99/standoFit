package com.standofit.back.shared.domain.bus.command;

/**
 * Bus interface for dispatching commands to their handlers.
 *
 * The CommandBus is responsible for routing commands to the appropriate
 * CommandHandler. This is part of the CQRS pattern.
 */
public interface CommandBus {

    /**
     * Dispatches the given command to its registered handler and returns the result.
     *
     * @param <R> the type of result expected
     * @param command the command to execute
     * @return the result from the handler
     */
    <R> R dispatch(Command<R> command);
}