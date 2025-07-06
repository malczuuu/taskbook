package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class CredentialsModel {

  private final String email;
  private final String password;

  @JsonCreator
  public CredentialsModel(
      @JsonProperty("email") String email, @JsonProperty("password") String password) {
    this.email = email;
    this.password = password;
  }

  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  @JsonProperty("password")
  public String getPassword() {
    return password;
  }
}
