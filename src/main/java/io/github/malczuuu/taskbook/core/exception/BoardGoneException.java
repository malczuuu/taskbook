package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.taskbook.core.exception.base.GoneException;

public class BoardGoneException extends GoneException {

  public BoardGoneException(Problem.Extension extension) {
    super("Board gone", extension);
  }
}
