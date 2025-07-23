package io.github.malczuuu.taskbook.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.malczuuu.taskbook.core.entity.Role;
import io.github.malczuuu.taskbook.core.entity.UserEntity;
import io.github.malczuuu.taskbook.core.repository.UserRepository;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UserDetailsServiceImplTests {

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
  private UserDetailsService userDetailsService;

  @BeforeEach
  void beforeEach() {
    userRepository = mock(UserRepository.class);
    userDetailsService = new UserDetailsServiceImpl(userRepository);
  }

  @Test
  void shouldGetUser() {
    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

    String username = "john.doe@example.org";
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    assertEquals(user.getEmail(), userDetails.getUsername());
    assertEquals(user.getPassword(), userDetails.getPassword());
    assertEquals(
        new HashSet<>(
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))),
        userDetails.getAuthorities());
  }

  @Test
  void shouldThrowAnExceptionOnNotExistingUser() {
    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

    String username = "john.doe@example.org";
    UsernameNotFoundException e =
        assertThrows(
            UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));

    assertEquals("username " + username + " not found", e.getMessage());
  }
}
