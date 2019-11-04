package io.github.malczuuu.taskbook.core.exception;

public class InvalidPasswordException extends InvalidCredentialsException {

  public InvalidPasswordException() {
    super("password");
  }
}
