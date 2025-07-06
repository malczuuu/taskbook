package io.github.malczuuu.taskbook.rest.support;

import io.github.malczuuu.taskbook.core.exception.ViolationException;

public class Pagination {

  public static Pagination process(String page, String size) {
    int convertedPage = Integer.parseInt(page);
    int convertedSize = Integer.parseInt(size);
    if (convertedPage < 0) {
      throw new ViolationException("page", "must be a non-negative number");
    }
    if (convertedSize < 1) {
      throw new ViolationException("size", "must be a positive number");
    }
    return new Pagination(convertedPage, convertedSize);
  }

  private final int page;
  private final int size;

  private Pagination(int page, int size) {
    this.page = page;
    this.size = size;
  }

  public int getPage() {
    return page;
  }

  public int getSize() {
    return size;
  }
}
