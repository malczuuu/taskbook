package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.taskbook.core.exception.base.GoneException;
import io.github.problem4j.core.Problem;

public class IssueGoneException extends GoneException {

  public IssueGoneException(Problem.Extension extension) {
    super("Issue gone", extension);
  }
}
