package com.standofit.back.shared.domain.bus;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.query.Query;

/**
 * Unified bus interface for handling both Queries and Commands in the CQRS pattern.
 * <p>
 * This bus provides a single entry point for all application operations:
 * - Queries (read operations) via ask()
 * - Commands (write operations) via execute()
 *
 * @author standofit
 * @version 1.0
 */
public interface ApplicationBus {

    /**
     * Dispatches a query to its handler and returns the result.
     * Used for read operations.
     *
     * @param <R>   the type of result expected
     * @param query the query to execute
     * @return the result from the query handler
     */
    <R> R ask(Query query);

    /**
     * Dispatches a command to its handler and returns the result.
     * Used for write operations.
     *
     * @param <R>     the type of result expected
     * @param command the command to execute
     * @return the result from the command handler
     */
    <R> R execute(Command<R> command);
}
