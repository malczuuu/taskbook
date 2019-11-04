package io.github.malczuuu.taskbook.security;

import io.github.malczuuu.taskbook.core.entity.Role;
import io.github.malczuuu.taskbook.core.entity.UserEntity;
import io.github.malczuuu.taskbook.core.repository.UserRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username == null) {
      throw usernameNotFoundException("null");
    }
    return fetchUser(username);
  }

  private UsernameNotFoundException usernameNotFoundException(String username) {
    return new UsernameNotFoundException("username " + username + " not found");
  }

  private UserDetails fetchUser(String username) {
    UserEntity user =
        userRepository.findByEmail(username).orElseThrow(() -> usernameNotFoundException(username));
    return new User(
        user.getEmail(), user.getPassword(), true, true, true, true, authorities(user.getRole()));
  }

  private List<GrantedAuthority> authorities(Role role) {
    switch (role) {
      case ADMIN:
        return Arrays.asList(
            new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
      case USER:
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
      default:
        throw new IllegalArgumentException("role " + role + " is not supported");
    }
  }
}
