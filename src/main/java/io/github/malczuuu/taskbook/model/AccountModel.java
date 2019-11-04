package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class AccountModel {

  private final String uid;
  private final String email;
  private final String role;
  private final String firstName;
  private final String lastName;

  @JsonCreator
  public AccountModel(
      @JsonProperty("uid") String uid,
      @JsonProperty("email") String email,
      @JsonProperty("role") String role,
      @JsonProperty("first_name") String firstName,
      @JsonProperty("last_name") String lastName) {
    this.uid = uid;
    this.email = email;
    this.role = role;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @NotBlank
  @JsonProperty("uid")
  public String getUid() {
    return uid;
  }

  @NotBlank
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  @NotNull
  @JsonProperty("role")
  public String getRole() {
    return role;
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
