package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class UserUpdateModel {

  private final String role;

  @JsonCreator
  public UserUpdateModel(@JsonProperty("role") String role) {
    this.role = role;
  }

  @JsonProperty("role")
  public String getRole() {
    return role;
  }
}
