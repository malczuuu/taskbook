package io.github.malczuuu.taskbook.core.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "issue_comments")
public class CommentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "issue_comment_id")
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "board_id", nullable = false, updatable = false)
  private BoardEntity board;

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

  public CommentEntity(
      BoardEntity board,
      IssueEntity issue,
      String content,
      UserEntity author,
      Instant createdTime) {
    this(null, board, issue, content, author, createdTime, null);
  }

  public CommentEntity(
      Long id,
      BoardEntity board,
      IssueEntity issue,
      String content,
      UserEntity author,
      Instant createdTime,
      Instant updatedTime) {
    this.id = id;
    this.board = board;
    this.issue = issue;
    this.content = content;
    this.author = author;
    this.createdTime = createdTime;
    this.updatedTime = updatedTime;
  }

  public Long getId() {
    return id;
  }

  public BoardEntity getBoard() {
    return board;
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
