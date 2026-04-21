package com.standofit.back.shared.infraestructure.controller;

import com.standofit.back.shared.domain.bus.ApplicationBus;
import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.query.Query;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Base controller class that provides common functionality for all REST controllers.
 *
 * This class provides convenient methods for interacting with the ApplicationBus
 * and creating standardized HTTP responses. All domain-specific controllers should
 * extend this class.
 *
 * Usage:
 * <pre>
 * &#64;RestController
 * &#64;RequestMapping("/api/workouts")
 * public class WorkoutController extends SharedController {
 *
 *     public WorkoutController(ApplicationBus applicationBus) {
 *         super(applicationBus);
 *     }
 *
 *     &#64;GetMapping("/{id}")
 *     public WorkoutDto getById(UUID id) {
 *         return ask(new GetWorkoutByIdQuery(id));
 *     }
 *
 *     &#64;PostMapping
 *     public UUID create(@RequestBody CreateWorkoutRequest request) {
 *         return execute(new CreateWorkoutCommand(...));
 *     }
 * }
 * </pre>
 *
 * @author standofit
 * @version 1.0
 */
@RestController
public abstract class SharedController {

    private final ApplicationBus applicationBus;

    protected SharedController(ApplicationBus applicationBus) {
        this.applicationBus = applicationBus;
    }

    /**
     * Dispatches a query to the ApplicationBus and returns the result.
     * Used for read operations.
     *
     * @param <R> the type of result expected
     * @param query the query to execute
     * @return the result from the query handler
     */
    protected <R> R ask(Query query) {
        return applicationBus.ask(query);
    }

    /**
     * Dispatches a command to the ApplicationBus and returns the result.
     * Used for write operations.
     *
     * @param <R> the type of result expected
     * @param command the command to execute
     * @return the result from the command handler
     */
    protected <R> R execute(Command<R> command) {
        return applicationBus.execute(command);
    }

    /**
     * Creates a successful HTTP response with the given data.
     *
     * @param data the data to include in the response body
     * @return ResponseEntity with HTTP 200 status
     */
    protected ResponseEntity<?> success(Object data) {
        return ResponseEntity.ok(data);
    }

    /**
     * Creates an error HTTP response with the given status and message.
     *
     * @param status the HTTP status code
     * @param message the error message
     * @return ResponseEntity with the specified status and error body
     */
    protected ResponseEntity<?> error(int status, String message) {
        return ResponseEntity.status(status).body(Map.of("error", message));
    }
}