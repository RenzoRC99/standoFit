package com.standofit.back.modules.training.planning.infrastructure.mapper;

import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureErrors;
import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class DatabaseExceptionMapper {

    public WorkoutInfrastructureException map(
            DataAccessException e, WorkoutInfrastructureErrors defaultError) {
        Throwable cause = e.getMostSpecificCause();

        return mapSqlException(cause)
                .or(() -> mapTimeoutException(cause))
                .or(() -> mapConstraintViolation(e))
                .orElse(
                        new WorkoutInfrastructureException(
                                defaultError.getMessage() + ": " + cause.getMessage()));
    }

    private Optional<WorkoutInfrastructureException> mapSqlException(Throwable cause) {
        if (!(cause instanceof SQLException sqlEx)) {
            return Optional.empty();
        }
        return Arrays.stream(WorkoutInfrastructureErrors.values())
                .filter(error -> error.getSqlState() != null)
                .filter(error -> error.matchesSqlState(sqlEx.getSQLState()))
                .findFirst()
                .map(error -> new WorkoutInfrastructureException(error.getMessage()));
    }

    private Optional<WorkoutInfrastructureException> mapTimeoutException(Throwable cause) {
        if (cause instanceof QueryTimeoutException) {
            return Optional.of(
                    new WorkoutInfrastructureException(WorkoutInfrastructureErrors.DB_TIMEOUT.getMessage()));
        }
        return Optional.empty();
    }

    private Optional<WorkoutInfrastructureException> mapConstraintViolation(DataAccessException e) {
        if (e instanceof DataIntegrityViolationException) {
            return Optional.of(
                    new WorkoutInfrastructureException(
                            WorkoutInfrastructureErrors.DB_CONSTRAINT_VIOLATION.getMessage()));
        }
        return Optional.empty();
    }
}
