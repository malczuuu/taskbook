package io.github.malczuuu.taskbook.core.exception;

import io.github.malczuuu.taskbook.core.exception.base.NotFoundException;

public class IssueNotFoundException extends NotFoundException {

  public IssueNotFoundException() {
    super("Issue not found");
  }
}
