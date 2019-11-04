package io.github.malczuuu.taskbook.core.exception.base;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.Problem.Extension;
import io.github.malczuuu.problem4j.core.ProblemBuilder;
import io.github.malczuuu.problem4j.core.ProblemException;
import org.springframework.http.HttpStatus;

public abstract class BadRequestException extends ProblemException {

  protected BadRequestException(String detail, Problem.Extension... extensions) {
    super(prepareProblem(detail, extensions));
  }

  private static Problem prepareProblem(String detail, Problem.Extension... extensions) {
    ProblemBuilder builder =
        Problem.builder()
            .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .status(HttpStatus.BAD_REQUEST.value())
            .detail(detail);
    for (Extension extension : extensions) {
      builder.extension(extension.getKey(), extension.getValue());
    }
    return builder.build();
  }
}
