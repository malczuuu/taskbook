package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.taskbook.core.exception.base.ConflictException;

public class BoardUidConflictException extends ConflictException {

  public BoardUidConflictException() {
    super("Board UID conflict");
  }
}
