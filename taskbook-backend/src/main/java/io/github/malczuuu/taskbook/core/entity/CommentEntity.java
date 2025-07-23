package io.github.malczuuu.taskbook.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "issue_comments")
public class CommentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "issue_comment_id")
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "issue_id", nullable = false, updatable = false)
  private IssueEntity issue;

  @Column(name = "issue_comment_content")
  private String content;

  @ManyToOne(optional = false)
  @JoinColumn(name = "author_user_id", nullable = false, updatable = false)
  private UserEntity author;

  @Column(name = "issue_comment_created_time", nullable = false, updatable = false)
  private Instant createdTime;

  @Column(name = "issue_comment_updated_time")
  private Instant updatedTime;

  public CommentEntity() {}

  public CommentEntity(IssueEntity issue, String content, UserEntity author, Instant createdTime) {
    this(null, issue, content, author, createdTime, null);
  }

  public CommentEntity(
      Long id,
      IssueEntity issue,
      String content,
      UserEntity author,
      Instant createdTime,
      Instant updatedTime) {
    this.id = id;
    this.issue = issue;
    this.content = content;
    this.author = author;
    this.createdTime = createdTime;
    this.updatedTime = updatedTime;
  }

  public Long getId() {
    return id;
  }

  public IssueEntity getIssue() {
    return issue;
  }

  public String getContent() {
    return content;
  }

  public UserEntity getAuthor() {
    return author;
  }

  public Instant getCreatedTime() {
    return createdTime;
  }

  public Instant getUpdatedTime() {
    return updatedTime;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setUpdatedTime(Instant updatedTime) {
    this.updatedTime = updatedTime;
  }
}
