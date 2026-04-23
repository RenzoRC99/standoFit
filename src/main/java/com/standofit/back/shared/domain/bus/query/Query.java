package com.standofit.back.shared.domain.bus.query;

/**
 * Base interface for all queries in the application.
 *
 * A Query represents a read-only operation that retrieves data without modifying state.
 * Each query should implement this interface and contain the data needed to perform the read.
 *
 * Example:
 * <pre>
 * public class GetWorkoutByIdQuery implements Query<WorkoutDto> {
 *     private final WorkoutId workoutId;
 *
 *     public GetWorkoutByIdQuery(UUID id) {
 *         this.workoutId = new WorkoutId(id);
 *     }
 *
 *     public WorkoutId getWorkoutId() {
 *         return workoutId;
 *     }
 * }
 * </pre>
 *
 * @param <R> the return type of the query result
 * @author standofit
 * @version 1.0
 */
public interface Query<R> {}