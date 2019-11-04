package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.taskbook.core.exception.base.BadRequestException;

public class UserDoesNotExistException extends BadRequestException {

  public UserDoesNotExistException() {
    super("User does not exist");
  }
}
