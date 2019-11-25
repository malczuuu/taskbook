package io.github.malczuuu.taskbook.core.impl;

import io.github.malczuuu.taskbook.core.entity.UserEntity;
import io.github.malczuuu.taskbook.core.exception.InvalidPasswordException;
import io.github.malczuuu.taskbook.core.exception.UserDoesNotExistException;
import io.github.malczuuu.taskbook.core.repository.UserRepository;
import io.github.malczuuu.taskbook.core.service.AccountService;
import io.github.malczuuu.taskbook.model.AccountModel;
import io.github.malczuuu.taskbook.model.AccountUpdateModel;
import io.github.malczuuu.taskbook.model.PasswordUpdateModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AccountServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public AccountModel getAccount(String username) {
    return toAccountModel(fetchUser(username));
  }

  private UserEntity fetchUser(String email) {
    return userRepository.findByEmail(email).orElseThrow(UserDoesNotExistException::new);
  }

  private AccountModel toAccountModel(UserEntity user) {
    return new AccountModel(
        user.getUid(),
        user.getEmail(),
        user.getRole().toString().toLowerCase(),
        user.getFirstName(),
        user.getLastName());
  }

  @Override
  @Transactional
  public AccountModel updateAccount(String username, AccountUpdateModel update) {
    UserEntity user = fetchUser(username);
    user.setEmail(update.getEmail());
    user.setFirstName(update.getFirstName());
    user.setLastName(update.getLastName());
    return toAccountModel(user);
  }

  @Override
  @Transactional
  public void updatePassword(String username, PasswordUpdateModel password) {
    UserEntity user = fetchUser(username);
    if (!passwordEncoder.matches(password.getOldPassword(), user.getPassword())) {
      throw new InvalidPasswordException();
    }
    user.setPassword(passwordEncoder.encode(password.getNewPassword()));
  }
}
