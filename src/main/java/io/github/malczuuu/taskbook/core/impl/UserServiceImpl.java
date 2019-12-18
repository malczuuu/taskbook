package io.github.malczuuu.taskbook.core.impl;

import io.github.malczuuu.taskbook.core.entity.IssueEntity;
import io.github.malczuuu.taskbook.core.entity.Role;
import io.github.malczuuu.taskbook.core.entity.UserEntity;
import io.github.malczuuu.taskbook.core.exception.RoleSelfUpdateAttemptException;
import io.github.malczuuu.taskbook.core.exception.SelfDeleteAttemptException;
import io.github.malczuuu.taskbook.core.exception.UserEmailConflictException;
import io.github.malczuuu.taskbook.core.exception.UserNotFoundException;
import io.github.malczuuu.taskbook.core.repository.IssueRepository;
import io.github.malczuuu.taskbook.core.repository.UserRepository;
import io.github.malczuuu.taskbook.core.service.UserService;
import io.github.malczuuu.taskbook.model.NewUserModel;
import io.github.malczuuu.taskbook.model.UserModel;
import io.github.malczuuu.taskbook.model.UserUpdateModel;
import java.util.List;
import java.util.UUID;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final IssueRepository issueRepository;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(
      UserRepository userRepository,
      IssueRepository issueRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.issueRepository = issueRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Page<UserModel> findAll(int page, int size) {
    return userRepository.findAll(PageRequest.of(page, size)).map(this::toUserModel);
  }

  @Override
  public Page<UserModel> findAll(String query, int page, int size) {
    return userRepository.findAllByQuery(query, PageRequest.of(page, size)).map(this::toUserModel);
  }

  private UserModel toUserModel(UserEntity user) {
    return new UserModel(
        user.getUid(),
        user.getEmail(),
        user.getRole().name().toLowerCase(),
        user.getFirstName(),
        user.getLastName());
  }

  @Override
  public UserModel findByUid(String uid) {
    return toUserModel(fetchUser(uid));
  }

  private UserEntity fetchUser(String uid) {
    return userRepository.findByUid(uid).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public UserModel create(NewUserModel user) {
    String uid = UUID.randomUUID().toString().replace("-", "");
    String password = passwordEncoder.encode(user.getPassword());
    UserEntity entity =
        new UserEntity(
            uid,
            user.getEmail(),
            password,
            Role.valueOf(user.getRole().toUpperCase()),
            user.getFirstName().trim(),
            user.getLastName().trim());
    try {
      entity = userRepository.save(entity);
    } catch (DataIntegrityViolationException e) {
      if (e.getCause() instanceof ConstraintViolationException) {
        String constraint = ((ConstraintViolationException) e.getCause()).getConstraintName();
        if ("unique_user_email".equals(constraint)) {
          throw new UserEmailConflictException();
        }
      }
      throw e;
    }
    return toUserModel(entity);
  }

  @Override
  @Transactional
  public UserModel updateByUid(String uid, UserUpdateModel user, String email) {
    UserEntity entity = fetchUser(uid);
    if (entity.getEmail().equals(email)) {
      throw new RoleSelfUpdateAttemptException();
    }
    entity.setRole(Role.valueOf(user.getRole().toUpperCase()));
    return toUserModel(entity);
  }

  @Override
  @Transactional
  public void deleteByUid(String uid, String email) {
    UserEntity entity = fetchUser(uid);
    if (entity.getEmail().equals(email)) {
      throw new SelfDeleteAttemptException();
    }
    Pageable pageable = PageRequest.of(0, 500);

    Page<IssueEntity> issues =
        issueRepository.findAllByAssigneeAndArchivedTimeNull(entity, pageable);
    unassign(issues.getContent());

    while (issues.hasNext()) {
      pageable = pageable.next();
      issues = issueRepository.findAllByAssigneeAndArchivedTimeNull(entity, pageable);
      unassign(issues.getContent());
    }

    userRepository.delete(entity);
  }

  private void unassign(List<IssueEntity> issues) {
    issues.forEach(issue -> issue.setAssignee(null));
  }

  @Override
  public boolean anyExists() {
    return userRepository.count() > 0;
  }
}
