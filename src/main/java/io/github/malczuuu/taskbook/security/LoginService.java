package io.github.malczuuu.taskbook.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.github.malczuuu.taskbook.core.entity.UserEntity;
import io.github.malczuuu.taskbook.core.exception.InvalidEmailException;
import io.github.malczuuu.taskbook.core.exception.InvalidPasswordException;
import io.github.malczuuu.taskbook.core.repository.UserRepository;
import io.github.malczuuu.taskbook.model.SessionModel;
import io.github.malczuuu.taskbook.security.jwt.JwtProperties;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final Algorithm algorithm;
  private final JwtProperties jwtProperties;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final Clock clock;

  public LoginService(
      Algorithm algorithm,
      JwtProperties jwtProperties,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      Clock clock) {
    this.algorithm = algorithm;
    this.jwtProperties = jwtProperties;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.clock = clock;
  }

  public SessionModel loginJwt(String email, String password) {
    UserEntity user = fetchAndAuthorizeUser(email, password);

    Instant now = Instant.now(clock);
    return new SessionModel(
        JWT.create()
            .withSubject(user.getEmail())
            .withIssuer(jwtProperties.getIssuer())
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(now.plusSeconds(jwtProperties.getLifetime())))
            .sign(algorithm));
  }

  private UserEntity fetchAndAuthorizeUser(String email, String password) {
    UserEntity user = userRepository.findByEmail(email).orElseThrow(InvalidEmailException::new);
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new InvalidPasswordException();
    }
    return user;
  }
}
