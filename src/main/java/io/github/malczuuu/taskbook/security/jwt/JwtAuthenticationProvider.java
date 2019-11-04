package io.github.malczuuu.taskbook.security.jwt;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final UserDetailsService userDetailsService;

  public JwtAuthenticationProvider(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (authentication == null || authentication.getPrincipal() == null) {
      throw new UsernameNotFoundException("null");
    }
    String username = authentication.getPrincipal().toString();
    UserDetails user = userDetailsService.loadUserByUsername(username);
    return new JwtAuthenticationToken(username, user.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return JwtAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
