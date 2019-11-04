package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public final class BoardUpdateModel {

  private final String name;
  private final String description;

  @JsonCreator
  public BoardUpdateModel(
      @JsonProperty("name") String name, @JsonProperty("description") String description) {
    this.name = name;
    this.description = description;
  }

  @NotNull
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @NotNull
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }
}
