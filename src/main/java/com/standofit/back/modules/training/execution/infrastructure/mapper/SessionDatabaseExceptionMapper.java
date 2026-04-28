package com.standofit.back.modules.training.execution.infrastructure.mapper;

import com.standofit.back.modules.training.execution.infrastructure.SessionInfrastructureErrors;
import com.standofit.back.modules.training.execution.infrastructure.SessionInfrastructureException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Component;

@Component
public class SessionDatabaseExceptionMapper {

  public SessionInfrastructureException map(Throwable e, SessionInfrastructureErrors defaultError) {
    Throwable cause = e instanceof DataAccessException dae ? dae.getMostSpecificCause() : e;

    return mapSqlException(cause)
        .or(() -> mapTimeoutException(cause))
        .or(() -> mapConstraintViolation(cause))
        .orElse(
            new SessionInfrastructureException(
                defaultError.getMessage() + ": " + cause.getMessage()));
  }

  private Optional<SessionInfrastructureException> mapSqlException(Throwable cause) {
    if (!(cause instanceof SQLException sqlEx)) {
      return Optional.empty();
    }
    return Arrays.stream(SessionInfrastructureErrors.values())
        .filter(error -> error.getSqlState() != null)
        .filter(error -> error.matchesSqlState(sqlEx.getSQLState()))
        .findFirst()
        .map(error -> new SessionInfrastructureException(error.getMessage()));
  }

  private Optional<SessionInfrastructureException> mapTimeoutException(Throwable cause) {
    if (cause instanceof QueryTimeoutException) {
      return Optional.of(
          new SessionInfrastructureException(SessionInfrastructureErrors.DB_TIMEOUT.getMessage()));
    }
    return Optional.empty();
  }

  private Optional<SessionInfrastructureException> mapConstraintViolation(Throwable cause) {
    if (cause instanceof DataIntegrityViolationException) {
      return Optional.of(
          new SessionInfrastructureException(
              SessionInfrastructureErrors.DB_CONSTRAINT_VIOLATION.getMessage()));
    }
    return Optional.empty();
  }
}
