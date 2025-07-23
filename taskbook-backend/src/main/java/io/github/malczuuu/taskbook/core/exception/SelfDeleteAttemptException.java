package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.taskbook.core.exception.base.BadRequestException;

public class SelfDeleteAttemptException extends BadRequestException {

  public SelfDeleteAttemptException() {
    super("Self-deleting not allowed");
  }
}
