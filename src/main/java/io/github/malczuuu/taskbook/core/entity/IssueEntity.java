package io.github.malczuuu.taskbook.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;

@Entity
@Table(
    name = "issues",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "unique_uid_within_board",
          columnNames = {"board_id", "issue_uid"})
    })
public class IssueEntity {

  public enum Status {
    TO_DO,
    IN_PROGRESS,
    DONE
  }

  @Id
  @Column(name = "issue_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "issue_uid", nullable = false)
  private String uid;

  @JoinColumn(name = "board_id", nullable = false)
  @ManyToOne(optional = false)
  private BoardEntity board;

  @Column(name = "issue_title", nullable = false)
  private String title;

  @Column(name = "issue_detail", nullable = false)
  private String detail;

  @JoinColumn(name = "assignee_user_id")
  @ManyToOne
  private UserEntity assignee;

  @Column(name = "issue_status", nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private Status status;

  @Column(name = "issue_archived_time")
  private Instant archivedTime;

  public IssueEntity() {}

  public IssueEntity(
      Long id,
      String uid,
      BoardEntity board,
      String title,
      String detail,
      UserEntity assignee,
      Status status) {
    this.id = id;
    this.uid = uid;
    this.board = board;
    this.title = title;
    this.detail = detail;
    this.assignee = assignee;
    this.status = status;
    this.archivedTime = null;
  }

  public IssueEntity(
      String uid,
      BoardEntity board,
      String title,
      String detail,
      UserEntity assignee,
      Status status) {
    this(null, uid, board, title, detail, assignee, status);
  }

  public Long getId() {
    return id;
  }

  public String getUid() {
    return uid;
  }

  public BoardEntity getBoard() {
    return board;
  }

  public String getTitle() {
    return title;
  }

  public String getDetail() {
    return detail;
  }

  public UserEntity getAssignee() {
    return assignee;
  }

  public Status getStatus() {
    return status;
  }

  public Instant getArchivedTime() {
    return archivedTime;
  }

  public void setBoard(BoardEntity board) {
    this.board = board;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public void setAssignee(UserEntity assignee) {
    this.assignee = assignee;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public void setArchivedTime(Instant archivedTime) {
    this.archivedTime = archivedTime;
  }
}
