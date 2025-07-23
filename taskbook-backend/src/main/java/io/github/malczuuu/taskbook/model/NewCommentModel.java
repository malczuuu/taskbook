package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public final class NewCommentModel {

  private final String content;

  @JsonCreator
  public NewCommentModel(@JsonProperty("content") String content) {
    this.content = content;
  }

  @NotBlank
  @JsonProperty("content")
  public String getContent() {
    return content;
  }
}
