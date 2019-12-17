package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.taskbook.core.exception.base.ConflictException;

public class UserEmailConflictException extends ConflictException {

  public UserEmailConflictException() {
    super("User email conflict");
  }
}
