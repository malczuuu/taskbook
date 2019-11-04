package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.taskbook.core.exception.base.BadRequestException;

abstract class InvalidCredentialsException extends BadRequestException {

  InvalidCredentialsException(String credential) {
    super("Invalid " + credential);
  }
}
