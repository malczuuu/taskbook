package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public final class IssueUpdateModel {

  private final String title;
  private final String detail;
  private final String assignee;
  private final String status;

  @JsonCreator
  public IssueUpdateModel(
      @JsonProperty("title") String title,
      @JsonProperty("detail") String detail,
      @JsonProperty("assignee") String assignee,
      @JsonProperty("status") String status) {
    this.title = title;
    this.detail = detail;
    this.assignee = assignee;
    this.status = status;
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

  @JsonProperty("assignee")
  public String getAssignee() {
    return assignee;
  }

  @NotNull
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }
}
