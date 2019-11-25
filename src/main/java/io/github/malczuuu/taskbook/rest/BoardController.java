package io.github.malczuuu.taskbook.rest;

import io.github.malczuuu.taskbook.core.service.BoardService;
import io.github.malczuuu.taskbook.model.BoardModel;
import io.github.malczuuu.taskbook.model.BoardUpdateModel;
import io.github.malczuuu.taskbook.rest.support.Pagination;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(path = "/api/boards")
public class BoardController {

  private final BoardService boardService;

  public BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping(produces = "application/json")
  public Page<BoardModel> findAll(
      @RequestParam(name = "page", defaultValue = "0")
          @Pattern(regexp = "^\\d+$", message = "must be a number")
          String page,
      @RequestParam(name = "size", defaultValue = "20")
          @Pattern(regexp = "^\\d+$", message = "must be a number")
          String size) {
    Pagination pagination = Pagination.process(page, size);
    return boardService.findAll(pagination.getPage(), pagination.getSize());
  }

  @GetMapping(
      produces = "application/json",
      params = {"name"})
  public Page<BoardModel> findAllFilterByName(
      @RequestParam("name") String name,
      @RequestParam(name = "page", defaultValue = "0")
          @Pattern(regexp = "^\\d+$", message = "must be a number")
          String page,
      @RequestParam(name = "size", defaultValue = "20")
          @Pattern(regexp = "^\\d+$", message = "must be a number")
          String size) {
    Pagination pagination = Pagination.process(page, size);
    return boardService.findAllFilterByName(name, pagination.getPage(), pagination.getSize());
  }

  @PostMapping(produces = "application/json", consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ADMIN')")
  public BoardModel create(@RequestBody @Valid BoardModel requestBody) {
    return boardService.create(requestBody);
  }

  @GetMapping(path = "/{uid}", produces = "application/json")
  public BoardModel findByUid(@PathVariable("uid") String uid) {
    return boardService.findByUid(uid);
  }

  @PutMapping(path = "/{uid}", produces = "application/json", consumes = "application/json")
  @PreAuthorize("hasRole('ADMIN')")
  public BoardModel updateByUid(
      @PathVariable("uid") String uid, @RequestBody @Valid BoardUpdateModel requestBody) {
    return boardService.updateByUid(uid, requestBody);
  }

  @DeleteMapping(path = "/{uid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteByUid(@PathVariable("uid") String uid) {
    boardService.deleteByUid(uid);
  }
}
