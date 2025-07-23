package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.taskbook.core.exception.base.BadRequestException;

public class RoleSelfUpdateAttemptException extends BadRequestException {

  public RoleSelfUpdateAttemptException() {
    super("Role self-update not allowed");
  }
}
