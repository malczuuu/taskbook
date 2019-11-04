package io.github.malczuuu.taskbook.rest;

import io.github.malczuuu.taskbook.model.CredentialsModel;
import io.github.malczuuu.taskbook.model.SessionModel;
import io.github.malczuuu.taskbook.security.LoginService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/login")
public class LoginController {

  private final LoginService loginService;

  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping(produces = "application/json", consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public SessionModel login(@RequestBody @Valid CredentialsModel requestBody) {
    return loginService.loginJwt(requestBody.getEmail(), requestBody.getPassword());
  }
}
