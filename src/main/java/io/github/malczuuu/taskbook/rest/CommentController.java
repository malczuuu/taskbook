package io.github.malczuuu.taskbook.rest;

import io.github.malczuuu.taskbook.core.service.CommentService;
import io.github.malczuuu.taskbook.model.CommentModel;
import io.github.malczuuu.taskbook.model.NewCommentModel;
import io.github.malczuuu.taskbook.rest.support.Pagination;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/boards/{board}/issues/{issue}/comments")
public class CommentController {

  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping(produces = "application/json")
  public Page<CommentModel> findAll(
      @PathVariable("board") String board,
      @PathVariable("issue") String issue,
      @RequestParam(name = "page", defaultValue = "0")
          @Pattern(regexp = "^\\d+$", message = "must be a number")
          String page,
      @RequestParam(name = "size", defaultValue = "20")
          @Pattern(regexp = "^\\d+$", message = "must be a number")
          String size) {
    Pagination pagination = Pagination.process(page, size);
    return commentService.findAll(board, issue, pagination.getPage(), pagination.getSize());
  }

  @PostMapping(produces = "application/json", consumes = "application/json")
  public CommentModel create(
      @PathVariable("board") String board,
      @PathVariable("issue") String issue,
      @RequestBody @Valid NewCommentModel requestBody,
      @AuthenticationPrincipal String principal) {
    return commentService.create(board, issue, requestBody, principal);
  }

  @GetMapping(path = "{uid}", produces = "application/json")
  public CommentModel findByUid(
      @PathVariable("board") String board,
      @PathVariable("issue") String issue,
      @PathVariable("uid") String uid) {
    return commentService.findByUid(board, issue, uid);
  }
}
