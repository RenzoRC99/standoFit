package com.standofit.back.shared.domain.bus.query;

/**
 * Base interface for all query handlers.
 * <p>
 * A QueryHandler is responsible for processing a specific Query and returning a result.
 * Each handler is associated with one Query type and returns one result type.
 *
 * @param <Q> The type of Query this handler processes, must extend Query
 * @param <R> The type of result this handler returns
 *            <p>
 *            Example:
 *            <pre>
 *            public class GetWorkoutByIdHandler implements QueryHandler<GetWorkoutByIdQuery, WorkoutDto> {
 *                private final WorkoutFinder finder;
 *
 *                public GetWorkoutByIdHandler(WorkoutFinder finder) {
 *                    this.finder = finder;
 *                }
 *
 *                &#64;Override
 *                public WorkoutDto handle(GetWorkoutByIdQuery query) {
 *                    return finder.findById(query.getWorkoutId());
 *                }
 *            }
 *            </pre>
 * @author standofit
 * @version 1.0
 */
public interface QueryHandler<Q extends Query<R>, R> {

    Class<Q> queryType();

    /**
     * Processes the given query and returns the result.
     *
     * @param query the query to process
     * @return the result of processing the query
     */
    R handle(Q query);
}
