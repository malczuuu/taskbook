package io.github.malczuuu.taskbook.security;

import io.github.malczuuu.taskbook.core.UserService;
import io.github.malczuuu.taskbook.model.NewUserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SecurityInitializer implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(SecurityInitializer.class);

  private final UserService userService;

  public SecurityInitializer(UserService userService) {
    this.userService = userService;
  }

  @Override
  @Transactional
  public void run(String... args) {
    String email = "admin@example.com";
    if (!userService.anyExists()) {
      String password = "admin";
      userService.create(new NewUserModel(email, password, "admin", "Admin", ""));
      log.info("Initial '{}' user with default password created", email);
    } else {
      log.debug("No need to create initial '{}' user", email);
    }
  }
}
