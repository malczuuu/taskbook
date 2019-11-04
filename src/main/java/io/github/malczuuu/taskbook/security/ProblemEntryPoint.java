package io.github.malczuuu.taskbook.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.malczuuu.problem4j.core.Problem;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class ProblemEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper mapper;

  public ProblemEntryPoint(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    attachWwwAuthenticate(request, response);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(Problem.CONTENT_TYPE);
    response
        .getWriter()
        .print(
            mapper.writeValueAsString(
                Problem.builder()
                    .title(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .build()));
  }

  private void attachWwwAuthenticate(HttpServletRequest request, HttpServletResponse response) {
    if (request.getHeader("Authorization") != null) {
      String method = request.getHeader("Authorization").split(" ", 2)[0];
      if (method.equals("Basic")) {
        response.addHeader("WWW-Authenticate", "Basic realm=\"Basic\"");
      }
    } else {
      response.addHeader("WWW-Authenticate", "Basic realm=\"Basic\"");
    }
  }
}
