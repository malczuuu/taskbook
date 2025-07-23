package io.github.malczuuu.taskbook.core.exception.base;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemException;
import org.springframework.http.HttpStatus;

public abstract class GoneException extends ProblemException {

  protected GoneException(String detail, Problem.Extension extension) {
    super(
        Problem.builder()
            .title(HttpStatus.GONE.getReasonPhrase())
            .status(HttpStatus.GONE.value())
            .detail(detail)
            .extension(extension.getKey(), extension.getValue())
            .build());
  }
}
