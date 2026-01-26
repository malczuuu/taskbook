package io.github.malczuuu.taskbook.core.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.malczuuu.taskbook.core.entity.Role;
import io.github.malczuuu.taskbook.core.entity.UserEntity;
import io.github.malczuuu.taskbook.core.exception.UserDoesNotExistException;
import io.github.malczuuu.taskbook.core.repository.UserRepository;
import io.github.malczuuu.taskbook.core.service.AccountService;
import io.github.malczuuu.taskbook.model.AccountModel;
import io.github.malczuuu.taskbook.model.AccountUpdateModel;
import io.github.malczuuu.taskbook.model.PasswordUpdateModel;
import io.github.problem4j.core.Problem;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

class AccountServiceImplTests {

  private final UserEntity user =
      new UserEntity(
          1L,
          "00000000000000000000000000000000",
          "john.doe@example.org",
          "password",
          Role.USER,
          "John",
          "Doe");

  private UserRepository userRepository;

  private AccountService accountService;

  @SuppressWarnings("deprecation")
  @BeforeEach
  void beforeEach() {
    userRepository = mock(UserRepository.class);
    accountService = new AccountServiceImpl(userRepository, NoOpPasswordEncoder.getInstance());
  }

  @Test
  void shouldReturnAccount() {
    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

    String username = "john.doe@example.org";
    AccountModel account = accountService.getAccount(username);

    assertEquals(user.getUid(), account.getUid());
    assertEquals(user.getEmail(), account.getEmail());
    assertEquals(user.getFirstName(), account.getFirstName());
    assertEquals(user.getLastName(), account.getLastName());
    assertEquals(user.getRole().toString().toLowerCase(), account.getRole());
  }

  @Test
  void shouldThrowAnExceptionOnNotExistingUser() {
    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

    String username = "john.doe@example.org";
    UserDoesNotExistException e =
        assertThrows(UserDoesNotExistException.class, () -> accountService.getAccount(username));

    assertEquals(Problem.BLANK_TYPE, e.getProblem().getType());
    assertEquals("Bad Request", e.getProblem().getTitle());
    assertEquals(400, e.getProblem().getStatus());
    assertEquals("User does not exist", e.getProblem().getDetail());
  }

  @Test
  void shouldUpdateAccount() {
    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    when(userRepository.save(any(UserEntity.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    String username = "john.doe@example.org";
    AccountUpdateModel update = new AccountUpdateModel("jane.doe@example.org", "Jane", "Doe");
    AccountModel account = accountService.updateAccount(username, update);

    assertEquals(update.getEmail(), account.getEmail());
    assertEquals(update.getFirstName(), account.getFirstName());
    assertEquals(update.getLastName(), account.getLastName());
  }

  @Test
  void shouldNotChangeAnythingMoreThanWhatRequested() {
    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    when(userRepository.save(any(UserEntity.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    String username = "john.doe@example.org";
    AccountUpdateModel update = new AccountUpdateModel("jane.doe@example.org", "Jane", "Doe");
    AccountModel account = accountService.updateAccount(username, update);

    assertEquals("00000000000000000000000000000000", account.getUid());
    assertEquals("user", account.getRole());
  }

  @Test
  void shouldThrowAnErrorOnNotExistingUserOnAccountUpdate() {
    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

    String username = "john.doe@example.org";
    AccountUpdateModel update = new AccountUpdateModel("jane.doe@example.org", "Jane", "Doe");

    UserDoesNotExistException e =
        assertThrows(
            UserDoesNotExistException.class, () -> accountService.updateAccount(username, update));

    assertEquals(Problem.BLANK_TYPE, e.getProblem().getType());
    assertEquals("Bad Request", e.getProblem().getTitle());
    assertEquals(400, e.getProblem().getStatus());
    assertEquals("User does not exist", e.getProblem().getDetail());
  }

  @Test
  void shouldUpdatePasswordOfUser() {
    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    when(userRepository.save(any(UserEntity.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    String username = "john.doe@example.org";
    PasswordUpdateModel update = new PasswordUpdateModel(user.getPassword(), "drowssap");
    accountService.updatePassword(username, update);

    assertEquals(user.getPassword(), update.getNewPassword());
  }

  @Test
  void shouldNotChangeAnythingMoreThanPassword() {
    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    when(userRepository.save(any(UserEntity.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    String username = "john.doe@example.org";
    PasswordUpdateModel update = new PasswordUpdateModel(user.getPassword(), "drowssap");
    accountService.updatePassword(username, update);

    assertEquals("00000000000000000000000000000000", user.getUid());
    assertEquals("john.doe@example.org", user.getEmail());
    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());
    assertEquals(Role.USER, user.getRole());
  }

  @Test
  void shouldThrowAnErrorOnNotExistingUserOnPasswordUpdate() {
    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

    String username = "john.doe@example.org";
    PasswordUpdateModel update = new PasswordUpdateModel(user.getPassword(), "drowssap");

    UserDoesNotExistException e =
        assertThrows(
            UserDoesNotExistException.class, () -> accountService.updatePassword(username, update));

    assertEquals(Problem.BLANK_TYPE, e.getProblem().getType());
    assertEquals("Bad Request", e.getProblem().getTitle());
    assertEquals(400, e.getProblem().getStatus());
    assertEquals("User does not exist", e.getProblem().getDetail());
  }
}
