package io.github.malczuuu.taskbook.rest;

import io.github.malczuuu.taskbook.core.service.IssueService;
import io.github.malczuuu.taskbook.model.IssueRawModel;
import io.github.malczuuu.taskbook.rest.support.Pagination;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(path = "/api/issues")
public class IssuesRawController {

  private final IssueService issuesRawService;

  public IssuesRawController(IssueService issuesRawService) {
    this.issuesRawService = issuesRawService;
  }

  @GetMapping(params = {"assignee"})
  public PagedModel<IssueRawModel> findIssuesAssignedTo(
      @RequestParam("assignee") String assignee,
      @RequestParam(name = "page", defaultValue = "0")
          @Pattern(regexp = "^\\d+$", message = "must be a number")
          String page,
      @RequestParam(name = "size", defaultValue = "20")
          @Pattern(regexp = "^\\d+$", message = "must be a number")
          String size) {
    Pagination pagination = Pagination.process(page, size);
    return new PagedModel<>(
        issuesRawService.findRawByAssignee(assignee, pagination.getPage(), pagination.getSize()));
  }
}
