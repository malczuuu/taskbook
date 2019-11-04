package io.github.malczuuu.taskbook.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfiguration {

  private static final Logger log = LoggerFactory.getLogger(JwtConfiguration.class);

  @Bean
  public Algorithm algorithm(JwtProperties jwtProperties) {
    String secret = jwtProperties.getSecret() != null ? jwtProperties.getSecret() : randomSecret();
    return Algorithm.HMAC256(secret);
  }

  private String randomSecret() {
    String random = UUID.randomUUID().toString();
    log.warn("Using generated smarthome.jwt.secret={}", random);
    return random;
  }
}
