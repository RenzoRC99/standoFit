package com.standofit.back.shared.domain.bus.command;

/**
 * Base interface for all Command Handlers in the CQRS pattern.
 * <p>
 * A CommandHandler processes a specific type of Command and returns a result.
 * The handler contains the business logic for executing the command.
 *
 * @param <C> the type of Command this handler processes
 * @param <R> the return type of the command execution
 */
public interface CommandHandler<C extends Command<R>, R> {


    Class<C> commandType();

    /**
     * Handles the execution of the given command.
     *
     * @param command the command to handle
     * @return the result of the command execution
     */
    R handle(C command);
}
