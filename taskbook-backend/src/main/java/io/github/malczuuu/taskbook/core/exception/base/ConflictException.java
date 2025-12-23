package io.github.malczuuu.taskbook.core.exception.base;

import io.github.problem4j.core.Problem;
import io.github.problem4j.core.ProblemException;
import org.springframework.http.HttpStatus;

public abstract class ConflictException extends ProblemException {

  protected ConflictException(String detail) {
    super(
        Problem.builder()
            .title(HttpStatus.CONFLICT.getReasonPhrase())
            .status(HttpStatus.CONFLICT.value())
            .detail(detail)
            .build());
  }
}
