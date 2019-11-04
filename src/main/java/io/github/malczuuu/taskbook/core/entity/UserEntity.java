package io.github.malczuuu.taskbook.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "unique_user_uid",
          columnNames = {"user_uid"}),
      @UniqueConstraint(
          name = "unique_user_email",
          columnNames = {"user_email"})
    })
public class UserEntity {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_uid", nullable = false, updatable = false)
  private String uid;

  @Column(name = "user_email", nullable = false)
  private String email;

  @Column(name = "user_password", nullable = false)
  private String password;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "user_role", nullable = false)
  private Role role;

  @Column(name = "user_first_name", nullable = false)
  private String firstName;

  @Column(name = "user_surname", nullable = false)
  private String lastName;

  public UserEntity() {}

  public UserEntity(
      Long id,
      String uid,
      String email,
      String password,
      Role role,
      String firstName,
      String lastName) {
    this.id = id;
    this.uid = uid;
    this.email = email;
    this.role = role;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public UserEntity(
      String uid, String email, String password, Role role, String firstName, String lastName) {
    this(null, uid, email, password, role, firstName, lastName);
  }

  public Long getId() {
    return id;
  }

  public String getUid() {
    return uid;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public Role getRole() {
    return role;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
