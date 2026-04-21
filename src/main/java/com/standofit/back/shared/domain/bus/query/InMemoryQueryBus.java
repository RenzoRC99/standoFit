package com.standofit.back.shared.domain.bus.query;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory implementation of the QueryBus.
 *
 * This implementation stores all query handlers in a Map, where the key is the Query class
 * and the value is the corresponding handler. When a query is dispatched, it looks up
 * the handler by the query class and executes it.
 *
 * This is a simple, synchronous implementation suitable for single-server deployments.
 * For distributed systems, consider implementing QueryBus with a message broker.
 *
 * Usage:
 * <pre>
 * // Register handlers (typically done via configuration)
 * InMemoryQueryBus bus = new InMemoryQueryBus();
 * bus.registerHandler(GetWorkoutByIdQuery.class, new GetWorkoutByIdHandler(...));
 *
 * // Dispatch query
 * WorkoutDto result = (WorkoutDto) bus.ask(new GetWorkoutByIdQuery(uuid));
 * </pre>
 *
 * @author standofit
 * @version 1.0
 */
public class InMemoryQueryBus implements QueryBus {

    private final Map<Class<? extends Query>, QueryHandler<?, ?>> handlers = new HashMap<>();

    /**
     * Registers a handler for a specific query class.
     *
     * @param queryClass the class of the query
     * @param handler the handler that will process the query
     */
    public void registerHandler(Class<? extends Query> queryClass, QueryHandler<?, ?> handler) {
        handlers.put(queryClass, handler);
    }

    /**
     * Dispatches the given query to its registered handler and returns the result.
     *
     * @param <R> the type of result expected
     * @param query the query to execute
     * @return the result from the handler
     * @throws IllegalStateException if no handler is registered for the query class
     */
    @Override
    public <R> R ask(Query query) {
        @SuppressWarnings("unchecked")
        QueryHandler<Query, R> handler = (QueryHandler<Query, R>) handlers.get(query.getClass());
        if (handler == null) {
            throw new IllegalStateException("No handler registered for: " + query.getClass().getSimpleName());
        }
        return handler.handle(query);
    }
}