package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class IssueModel {

  private final String uid;
  private final String title;
  private final String detail;
  private final UserModel assignee;
  private final String status;

  @JsonCreator
  public IssueModel(
      @JsonProperty("uid") String uid,
      @JsonProperty("title") String title,
      @JsonProperty("detail") String detail,
      @JsonProperty("assignee") UserModel assignee,
      @JsonProperty("status") String status) {
    this.uid = uid;
    this.title = title;
    this.detail = detail;
    this.assignee = assignee;
    this.status = status;
  }

  @NotBlank
  @JsonProperty("uid")
  public String getUid() {
    return uid;
  }

  @NotNull
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  @NotNull
  @JsonProperty("detail")
  public String getDetail() {
    return detail;
  }

  @Valid
  @JsonProperty("assignee")
  public UserModel getAssignee() {
    return assignee;
  }

  @NotNull
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }
}
