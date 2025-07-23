package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.taskbook.core.exception.base.GoneException;

public class IssueGoneException extends GoneException {

  public IssueGoneException(Problem.Extension extension) {
    super("Issue gone", extension);
  }
}
