package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public final class SessionModel {

  private final String token;
  private final String expireDate;

  @JsonCreator
  public SessionModel(
      @JsonProperty("token") String token, @JsonProperty("expire_date") String expireDate) {
    this.token = token;
    this.expireDate = expireDate;
  }

  @NotBlank
  @JsonProperty("token")
  public String getToken() {
    return token;
  }

  @NotBlank
  @JsonProperty("expire_date")
  public String getExpireDate() {
    return expireDate;
  }

  @Override
  public String toString() {
    return token;
  }
}
