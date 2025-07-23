package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.taskbook.core.exception.base.NotFoundException;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException() {
    super("User not found");
  }
}
