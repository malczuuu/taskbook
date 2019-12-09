package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.taskbook.core.exception.base.NotFoundException;

public class CommentNotFoundException extends NotFoundException {

  public CommentNotFoundException() {
    super("Comment not found");
  }
}
