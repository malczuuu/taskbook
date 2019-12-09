package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class IssueRawModel {

  private final String boardUid;
  private final String boardName;
  private final String uid;
  private final String title;
  private final String detail;
  private final UserModel assignee;
  private final String status;

  @JsonCreator
  public IssueRawModel(
      @JsonProperty("board_uid") String boardUid,
      @JsonProperty("board_name") String boardName,
      @JsonProperty("uid") String uid,
      @JsonProperty("title") String title,
      @JsonProperty("detail") String detail,
      @JsonProperty("assignee") UserModel assignee,
      @JsonProperty("status") String status) {
    this.boardUid = boardUid;
    this.boardName = boardName;
    this.uid = uid;
    this.title = title;
    this.detail = detail;
    this.assignee = assignee;
    this.status = status;
  }

  @NotBlank
  @JsonProperty("board_uid")
  public String getBoardUid() {
    return boardUid;
  }

  @NotBlank
  @JsonProperty("board_name")
  public String getBoardName() {
    return boardName;
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
