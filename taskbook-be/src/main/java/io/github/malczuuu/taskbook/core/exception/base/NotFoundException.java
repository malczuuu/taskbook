package io.github.malczuuu.taskbook.core.exception.base;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemException;
import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends ProblemException {
  protected NotFoundException(String detail) {
    super(
        Problem.builder()
            .title(HttpStatus.NOT_FOUND.getReasonPhrase())
            .status(HttpStatus.NOT_FOUND.value())
            .detail(detail)
            .build());
  }
}
