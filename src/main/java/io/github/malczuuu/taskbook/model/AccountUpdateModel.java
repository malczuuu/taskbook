package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class AccountUpdateModel {

  private final String email;
  private final String firstName;
  private final String lastName;

  @JsonCreator
  public AccountUpdateModel(
      @JsonProperty("email") String email,
      @JsonProperty("first_name") String firstName,
      @JsonProperty("last_name") String lastName) {
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @NotBlank
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  @NotNull
  @JsonProperty("first_name")
  public String getFirstName() {
    return firstName;
  }

  @NotNull
  @JsonProperty("last_name")
  public String getLastName() {
    return lastName;
  }
}
