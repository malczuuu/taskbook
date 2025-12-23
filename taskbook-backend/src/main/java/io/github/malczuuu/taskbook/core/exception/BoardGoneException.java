package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.taskbook.core.exception.base.GoneException;
import io.github.problem4j.core.Problem;

public class BoardGoneException extends GoneException {

  public BoardGoneException(Problem.Extension extension) {
    super("Board gone", extension);
  }
}
