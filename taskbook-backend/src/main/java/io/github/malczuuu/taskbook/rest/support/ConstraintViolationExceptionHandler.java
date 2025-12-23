package io.github.malczuuu.taskbook.rest.support;

import io.github.problem4j.core.Problem;
import io.github.problem4j.core.ProblemBuilder;
import io.github.problem4j.spring.web.parameter.Violation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConstraintViolationExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Problem> handleProblemException(ConstraintViolationException ex) {
    ProblemBuilder problem =
        Problem.builder()
            .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .status(HttpStatus.BAD_REQUEST.value())
            .detail("Request parameters and/or body is not valid");

    problem.extension("violations", retrieveViolationsFrom(ex));

    return new ResponseEntity<>(problem.build(), HttpStatus.BAD_REQUEST);
  }

  private List<Violation> retrieveViolationsFrom(ConstraintViolationException ex) {
    return ex.getConstraintViolations().stream()
        .map(
            violation ->
                new Violation(violation.getPropertyPath().toString(), violation.getMessage()))
        .collect(Collectors.toList());
  }
}
