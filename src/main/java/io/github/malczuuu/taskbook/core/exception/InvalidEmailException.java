package io.github.malczuuu.taskbook.core.exception;

public class InvalidEmailException extends InvalidCredentialsException {

  public InvalidEmailException() {
    super("email");
  }
}
