package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.taskbook.core.exception.base.NotFoundException;

public class BoardNotFoundException extends NotFoundException {

  public BoardNotFoundException() {
    super("Board not found");
  }
}
