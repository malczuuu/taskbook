package io.github.malczuuu.taskbook.core.service;

import io.github.malczuuu.taskbook.model.NewUserModel;
import io.github.malczuuu.taskbook.model.UserModel;
import io.github.malczuuu.taskbook.model.UserUpdateModel;
import org.springframework.data.domain.Page;

public interface UserService {

  Page<UserModel> findAll(int page, int size);

  Page<UserModel> findAll(String query, int page, int size);

  UserModel findByUid(String uid);

  UserModel create(NewUserModel user);

  UserModel updateByUid(String uid, UserUpdateModel user, String email);

  void deleteByUid(String uid, String email);

  boolean anyExists();
}
