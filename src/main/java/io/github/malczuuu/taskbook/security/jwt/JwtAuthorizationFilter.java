package io.github.malczuuu.taskbook.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private final JwtProperties jwtProperties;
  private final Algorithm algorithm;

  public JwtAuthorizationFilter(
      AuthenticationManager authenticationManager,
      AuthenticationEntryPoint authenticationEntryPoint,
      JwtProperties jwtProperties,
      Algorithm algorithm) {
    super(authenticationManager, authenticationEntryPoint);
    this.jwtProperties = jwtProperties;
    this.algorithm = algorithm;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String header = request.getHeader(jwtProperties.getHeader());

    if (header == null || !header.startsWith(jwtProperties.getMethod() + " ")) {
      chain.doFilter(request, response);
      return;
    }

    try {
      SecurityContextHolder.getContext()
          .setAuthentication(getAuthenticationManager().authenticate(authentication(request)));
      chain.doFilter(request, response);
    } catch (JWTVerificationException e) {
      getAuthenticationEntryPoint()
          .commence(request, response, new BadCredentialsException("provided JWT is not valid", e));
    }
  }

  private JwtAuthenticationToken authentication(HttpServletRequest request) {
    String token = request.getHeader(jwtProperties.getHeader());
    if (token != null) {
      String user =
          JWT.require(algorithm)
              .build()
              .verify(token.replace(jwtProperties.getMethod() + " ", ""))
              .getSubject();
      if (user != null) {
        return new JwtAuthenticationToken(user, new ArrayList<>());
      }
    }
    return null;
  }
}
