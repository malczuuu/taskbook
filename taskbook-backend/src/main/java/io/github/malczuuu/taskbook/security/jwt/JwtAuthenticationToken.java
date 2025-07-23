package io.github.malczuuu.taskbook.security.jwt;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

  private final String principal;

  public JwtAuthenticationToken(
      String principal, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
  }

  @Override
  public Object getCredentials() {
    return "";
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }
}
