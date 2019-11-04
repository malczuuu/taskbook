package io.github.malczuuu.taskbook.security;

import com.auth0.jwt.algorithms.Algorithm;
import io.github.malczuuu.taskbook.security.jwt.JwtAuthenticationProvider;
import io.github.malczuuu.taskbook.security.jwt.JwtAuthorizationFilter;
import io.github.malczuuu.taskbook.security.jwt.JwtProperties;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final ProblemEntryPoint problemEntryPoint;
  private final JwtAuthenticationProvider jwtAuthenticationProvider;
  private final Algorithm algorithm;
  private final JwtProperties properties;

  public WebSecurityConfiguration(
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder,
      ProblemEntryPoint problemEntryPoint,
      JwtAuthenticationProvider jwtAuthenticationProvider,
      Algorithm algorithm,
      JwtProperties properties) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
    this.problemEntryPoint = problemEntryPoint;
    this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    this.algorithm = algorithm;
    this.properties = properties;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/api/login")
        .permitAll()
        .anyRequest()
        .hasRole("USER")
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .httpBasic()
        .authenticationEntryPoint(problemEntryPoint)
        .and()
        .addFilter(jwtAuthorizationFilter())
        .userDetailsService(userDetailsService)
        .csrf()
        .disable();
  }

  private JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
    return new JwtAuthorizationFilter(
        authenticationManager(), problemEntryPoint, properties, algorithm);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(jwtAuthenticationProvider)
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
  }
}
