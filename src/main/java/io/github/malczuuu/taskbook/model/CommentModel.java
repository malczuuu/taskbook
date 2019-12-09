package io.github.malczuuu.taskbook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class CommentModel {

  private final String uid;
  private final String content;
  private final UserModel author;
  private final String createdTime;
  private final String updatedTime;

  @JsonCreator
  public CommentModel(
      @JsonProperty("uid") String uid,
      @JsonProperty("content") String content,
      @JsonProperty("author") UserModel author,
      @JsonProperty("created_time") String createdTime,
      @JsonProperty("updated_time") String updatedTime) {
    this.uid = uid;
    this.content = content;
    this.author = author;
    this.createdTime = createdTime;
    this.updatedTime = updatedTime;
  }

  @NotBlank
  @JsonProperty("uid")
  public String getUid() {
    return uid;
  }

  @NotBlank
  @JsonProperty("content")
  public String getContent() {
    return content;
  }

  @Valid
  @NotNull
  @JsonProperty("author")
  public UserModel getAuthor() {
    return author;
  }

  @NotBlank
  @JsonProperty("created_time")
  public String getCreatedTime() {
    return createdTime;
  }

  @JsonProperty("updated_time")
  public String getUpdatedTime() {
    return updatedTime;
  }
}
