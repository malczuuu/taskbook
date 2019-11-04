package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

public final class SessionModel {

  private final String token;

  @JsonCreator
  public SessionModel(@JsonProperty("token") String token) {
    this.token = token;
  }

  @NotBlank
  @JsonProperty("token")
  public String getToken() {
    return token;
  }

  @Override
  public String toString() {
    return token;
  }
}
