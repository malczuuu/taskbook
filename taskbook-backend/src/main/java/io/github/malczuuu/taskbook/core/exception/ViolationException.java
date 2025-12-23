package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.taskbook.core.exception.base.BadRequestException;
import io.github.problem4j.core.Problem;
import io.github.problem4j.spring.web.parameter.Violation;

public class ViolationException extends BadRequestException {

  public ViolationException(String path, String error) {
    super(
        "Validation failed for fields " + path,
        Problem.extension("errors", new Violation(path, error)));
  }
}
