package io.github.malczuuu.taskbook.rest.support;

import io.github.malczuuu.problem4j.core.Problem;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/error")
public class ProblemErrorController implements ErrorController {

  @GetMapping(produces = "application/problem+json")
  public ResponseEntity<Problem> handle(HttpServletRequest request) {
    HttpStatus status = readErrorStatusFrom(request);

    Problem problem =
        Problem.builder().title(status.getReasonPhrase()).status(status.value()).build();

    return new ResponseEntity<>(problem, status);
  }

  private HttpStatus readErrorStatusFrom(HttpServletRequest request) {
    Integer status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    return HttpStatus.valueOf(status);
  }
}
