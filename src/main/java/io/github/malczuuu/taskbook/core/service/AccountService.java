package io.github.malczuuu.taskbook.core.service;

import io.github.malczuuu.taskbook.model.AccountModel;
import io.github.malczuuu.taskbook.model.AccountUpdateModel;
import io.github.malczuuu.taskbook.model.PasswordUpdateModel;

public interface AccountService {

  AccountModel getAccount(String username);

  AccountModel updateAccount(String username, AccountUpdateModel update);

  void updatePassword(String username, PasswordUpdateModel password);
}
