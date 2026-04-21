package com.standofit.back.shared.domain.bus.query;

/**
 * Contract for the query bus in the application.
 *
 * The QueryBus is responsible for dispatching queries to their appropriate handlers.
 * It acts as a central coordinator that routes queries to the correct QueryHandler.
 *
 * Implementation note:
 * The QueryBus should be implemented as a singleton Spring bean that all controllers
 * can inject to execute queries.
 *
 * Example usage:
 * <pre>
 * &#64;RestController
 * public class WorkoutController extends SharedController {
 *     public WorkoutController(QueryBus queryBus) {
 *         super(queryBus);
 *     }
 *
 *     &#64;GetMapping("/workouts/{id}")
 *     public WorkoutDto getById(&#64;PathVariable UUID id) {
 *         return ask(new GetWorkoutByIdQuery(id));
 *     }
 * }
 * </pre>
 *
 * @author standofit
 * @version 1.0
 */
public interface QueryBus {

    /**
     * Dispatches a query to its corresponding handler and returns the result.
     *
     * @param <R> the type of result expected
     * @param query the query to execute
     * @return the result of the query execution
     * @throws IllegalStateException if no handler is registered for the query type
     */
    <R> R ask(Query query);
}