package io.github.malczuuu.taskbook.core.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
    name = "boards",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "unique_board_uid",
          columnNames = {"board_uid"})
    })
public class BoardEntity {

  @Id
  @Column(name = "board_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "board_uid", nullable = false)
  private String uid;

  @Column(name = "board_name", nullable = false)
  private String name;

  @Column(name = "board_description", nullable = false, columnDefinition = "TEXT")
  private String description;

  @Column(name = "board_archived_time")
  private Instant archivedTime;

  public BoardEntity() {}

  public BoardEntity(String uid, String name, String description) {
    this(null, uid, name, description);
  }

  public BoardEntity(Long id, String uid, String name, String description) {
    this.id = id;
    this.uid = uid;
    this.name = name;
    this.description = description;
    this.archivedTime = null;
  }

  public Long getId() {
    return id;
  }

  public String getUid() {
    return uid;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Instant getArchivedTime() {
    return archivedTime;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setArchivedTime(Instant archivedTime) {
    this.archivedTime = archivedTime;
  }
}
