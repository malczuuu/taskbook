package io.github.malczuuu.taskbook.rest;

import io.github.malczuuu.taskbook.core.IssueService;
import io.github.malczuuu.taskbook.model.IssueModel;
import io.github.malczuuu.taskbook.model.IssueUpdateModel;
import io.github.malczuuu.taskbook.model.NewIssueModel;
import io.github.malczuuu.taskbook.rest.support.Pagination;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

@RestController
@RequestMapping(path = "/api/boards/{board}/issues")
public class IssueController {

  private final IssueService issueService;

  public IssueController(IssueService issueService) {
    this.issueService = issueService;
  }

  @GetMapping(produces = "application/json")
  public Page<IssueModel> findAll(
      @PathVariable("board") String board,
      @RequestParam(name = "page", defaultValue = "0")
          @Pattern(regexp = "^\\d+$", message = "must be a number")
          String page,
      @RequestParam(name = "size", defaultValue = "20")
          @Pattern(regexp = "^\\d+$", message = "must be a number")
          String size) {
    Pagination pagination = Pagination.process(page, size);
    return issueService.findAll(board, pagination.getPage(), pagination.getSize());
  }

  @GetMapping(
      produces = "application/json",
      params = {"title"})
  public Page<IssueModel> findAllFilterByTitle(
      @PathVariable("board") String board,
      @RequestParam("title") String title,
      @RequestParam(name = "page", defaultValue = "0")
          @Pattern(regexp = "^\\d+$", message = "must be a number")
          String page,
      @RequestParam(name = "size", defaultValue = "20")
          @Pattern(regexp = "^\\d+$", message = "must be a number")
          String size) {
    Pagination pagination = Pagination.process(page, size);
    return issueService.findAllFilterByTitle(
        board, title, pagination.getPage(), pagination.getSize());
  }

  @PostMapping(produces = "application/json", consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public IssueModel create(
      @PathVariable("board") String board, @RequestBody @Valid NewIssueModel requestBody) {
    return issueService.create(board, requestBody);
  }

  @GetMapping(path = "/{uid}", produces = "application/json")
  public IssueModel findByUid(
      @PathVariable("board") String board, @PathVariable("uid") String uid) {
    return issueService.findByUid(board, uid);
  }

  @PutMapping(path = "/{uid}", produces = "application/json", consumes = "application/json")
  public IssueModel updateByUid(
      @PathVariable("board") String board,
      @PathVariable("uid") String uid,
      @RequestBody @Valid IssueUpdateModel requestBody) {
    return issueService.updateByUid(board, uid, requestBody);
  }

  @DeleteMapping(path = "/{uid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteByUid(@PathVariable("board") String board, @PathVariable("uid") String uid) {
    issueService.deleteByUid(board, uid);
  }
}
