package io.github.malczuuu.taskbook.security;

import io.github.problem4j.core.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccessDeniedExceptionHandler {

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Problem> handleProblemException(AccessDeniedException ex) {
    HttpStatus status = HttpStatus.FORBIDDEN;
    Problem problem =
        Problem.builder().title(status.getReasonPhrase()).status(status.value()).build();
    return new ResponseEntity<>(problem, status);
  }
}
