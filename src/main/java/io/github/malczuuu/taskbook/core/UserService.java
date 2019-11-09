package io.github.malczuuu.taskbook.core;

import io.github.malczuuu.taskbook.core.entity.IssueEntity;
import io.github.malczuuu.taskbook.core.repository.IssueRepository;
import io.github.malczuuu.taskbook.core.entity.Role;
import io.github.malczuuu.taskbook.core.entity.UserEntity;
import io.github.malczuuu.taskbook.core.repository.UserRepository;
import io.github.malczuuu.taskbook.core.exception.UserNotFoundException;
import io.github.malczuuu.taskbook.model.NewUserModel;
import io.github.malczuuu.taskbook.model.UserModel;
import io.github.malczuuu.taskbook.model.UserUpdateModel;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final IssueRepository issueRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(
      UserRepository userRepository,
      IssueRepository issueRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.issueRepository = issueRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Page<UserModel> findAll(int page, int size) {
    return userRepository.findAll(PageRequest.of(page, size)).map(this::toUserModel);
  }

  public Page<UserModel> findAll(String query, int page, int size) {
    return userRepository.findAllByQuery(query,PageRequest.of(page,size)).map(this::toUserModel);
  }

  private UserModel toUserModel(UserEntity user) {
    return new UserModel(
        user.getUid(),
        user.getEmail(),
        user.getRole().name().toLowerCase(),
        user.getFirstName(),
        user.getLastName());
  }

  public UserModel findByUid(String uid) {
    return toUserModel(fetchUser(uid));
  }

  private UserEntity fetchUser(String uid) {
    return userRepository.findByUid(uid).orElseThrow(UserNotFoundException::new);
  }

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
    entity = userRepository.save(entity);
    return toUserModel(entity);
  }

  @Transactional
  public UserModel updateByUid(String uid, UserUpdateModel user) {
    UserEntity entity = fetchUser(uid);
    entity.setRole(Role.valueOf(user.getRole().toUpperCase()));
    return toUserModel(entity);
  }

  @Transactional
  public void deleteByUid(String uid) {
    UserEntity entity = fetchUser(uid);
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

  public boolean anyExists() {
    return userRepository.count() > 0;
  }
}
