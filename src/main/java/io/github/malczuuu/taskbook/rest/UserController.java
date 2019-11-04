package io.github.malczuuu.taskbook.rest;

import io.github.malczuuu.taskbook.core.UserService;
import io.github.malczuuu.taskbook.model.NewUserModel;
import io.github.malczuuu.taskbook.model.UserModel;
import io.github.malczuuu.taskbook.model.UserUpdateModel;
import io.github.malczuuu.taskbook.rest.support.Pagination;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(produces = "application/json")
  public Page<UserModel> findAll(
      @RequestParam(name = "page", defaultValue = "0")
          @Pattern(regexp = "^\\d+$", message = "must be a number")
          String page,
      @RequestParam(name = "size", defaultValue = "20")
          @Pattern(regexp = "^\\d+$", message = "must be a number")
          String size) {
    Pagination pagination = Pagination.process(page, size);
    return userService.findAll(pagination.getPage(), pagination.getSize());
  }

  @PostMapping(produces = "application/json", consumes = "application/json")
  public UserModel create(@RequestBody @Valid NewUserModel requestBody) {
    return userService.create(requestBody);
  }

  @GetMapping(path = "/{uid}", produces = "application/json")
  public UserModel findByUid(@PathVariable("uid") String uid) {
    return userService.findByUid(uid);
  }

  @PutMapping(path = "/{uid}", produces = "application/json", consumes = "application/json")
  public UserModel updateByUid(
      @PathVariable("uid") String uid, @RequestBody @Valid UserUpdateModel requestBody) {
    return userService.updateByUid(uid, requestBody);
  }

  @DeleteMapping(path = "/{uid}")
  public void deleteByUid(@PathVariable("uid") String uid) {
    userService.deleteByUid(uid);
  }
}
