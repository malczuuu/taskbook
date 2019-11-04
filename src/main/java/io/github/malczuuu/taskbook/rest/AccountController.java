package io.github.malczuuu.taskbook.rest;

import io.github.malczuuu.taskbook.core.AccountService;
import io.github.malczuuu.taskbook.model.AccountModel;
import io.github.malczuuu.taskbook.model.AccountUpdateModel;
import io.github.malczuuu.taskbook.model.PasswordUpdateModel;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/account")
public class AccountController {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping(produces = "application/json")
  public AccountModel getAccount(@AuthenticationPrincipal String principal) {
    return accountService.getAccount(principal);
  }

  @PutMapping(produces = "application/json", consumes = "application/json")
  public AccountModel updateAccount(
      @AuthenticationPrincipal String principal,
      @RequestBody @Valid AccountUpdateModel requestBody) {
    return accountService.updateAccount(principal, requestBody);
  }

  @PutMapping(path = "/password", consumes = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updatePassword(
      @AuthenticationPrincipal String principal,
      @RequestBody @Valid PasswordUpdateModel requestBody) {
    accountService.updatePassword(principal, requestBody);
  }
}
