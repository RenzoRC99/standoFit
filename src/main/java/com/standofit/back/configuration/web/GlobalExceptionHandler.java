package com.standofit.back.configuration.web;

import com.standofit.back.configuration.web.dto.ErrorDTO;
import com.standofit.back.modules.training.planning.infrastructure.WorkoutNotFoundException;
import com.standofit.back.shared.domain.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DomainException.class)
  public ResponseEntity<ErrorDTO> handleDomainException(DomainException ex) {
    ErrorDTO error = new ErrorDTO("INVALID_ARGUMENT", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
    ErrorDTO error = new ErrorDTO("INVALID_ARGUMENT", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(WorkoutNotFoundException.class)
  public ResponseEntity<ErrorDTO> handleWorkoutNotFoundException(WorkoutNotFoundException ex) {
    ErrorDTO error = new ErrorDTO("NOT_FOUND", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorDTO> handleRuntimeException(RuntimeException ex) {
    ErrorDTO error = new ErrorDTO("INTERNAL_ERROR", ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
