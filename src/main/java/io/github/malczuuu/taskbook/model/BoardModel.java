package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class BoardModel {

  private final String uid;
  private final String name;
  private final String description;

  @JsonCreator
  public BoardModel(
      @JsonProperty("uid") String uid,
      @JsonProperty("name") String name,
      @JsonProperty("description") String description) {
    this.uid = uid;
    this.name = name;
    this.description = description;
  }

  @NotBlank
  @JsonProperty("uid")
  public String getUid() {
    return uid;
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
