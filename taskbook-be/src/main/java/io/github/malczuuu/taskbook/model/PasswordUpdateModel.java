package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class PasswordUpdateModel {

  private final String oldPassword;
  private final String newPassword;

  @JsonCreator
  public PasswordUpdateModel(
      @JsonProperty("old_password") String oldPassword,
      @JsonProperty("new_password") String newPassword) {
    this.oldPassword = oldPassword;
    this.newPassword = newPassword;
  }

  @JsonIgnore
  public String getOldPassword() {
    return oldPassword;
  }

  @JsonIgnore
  public String getNewPassword() {
    return newPassword;
  }
}
