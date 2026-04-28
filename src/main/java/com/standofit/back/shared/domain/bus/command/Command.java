package com.standofit.back.shared.domain.bus.command;

/**
 * Base interface for all Commands in the CQRS pattern.
 *
 * <p>A Command represents an intention to change state. Unlike Queries, Commands are write
 * operations that modify the domain model.
 *
 * <p>Each command should be a specific, focused action like: - CreateWorkoutCommand -
 * UpdateWorkoutCommand - DeleteWorkoutCommand
 *
 * @param <R> the return type of the command handler result
 */
public interface Command<R> {}
