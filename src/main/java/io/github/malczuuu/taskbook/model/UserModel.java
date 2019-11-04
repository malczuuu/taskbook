package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class UserModel {

  private final String uid;
  private final String email;
  private final String role;
  private final String firstName;
  private final String lastName;

  @JsonCreator
  public UserModel(
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

  @JsonProperty("uid")
  public String getUid() {
    return uid;
  }

  @JsonProperty("email")
  public String getEmail() {
    return email;
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
