package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class NewUserModel {

  private final String email;
  private final String password;
  private final String role;
  private final String firstName;
  private final String lastName;

  @JsonCreator
  public NewUserModel(
      @JsonProperty("email") String email,
      @JsonProperty("password") String password,
      @JsonProperty("role") String role,
      @JsonProperty("first_name") String firstName,
      @JsonProperty("last_name") String lastName) {
    this.email = email;
    this.password = password;
    this.role = role;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @JsonProperty("role")
  public String getRole() {
    return role;
  }

  @JsonProperty("first_name")
  public String getFirstName() {
    return firstName;
  }

  @JsonProperty("last_name")
  public String getLastName() {
    return lastName;
  }
}
