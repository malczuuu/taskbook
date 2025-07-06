package io.github.malczuuu.taskbook.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "taskbook.jwt")
public class JwtProperties {

  private String issuer = "taskbook";
  private String secret = null;
  private String method = "Bearer";
  private String header = "Authorization";
  private int lifetime = 300;

  public String getIssuer() {
    return issuer;
  }

  public String getSecret() {
    return secret;
  }

  public String getMethod() {
    return method;
  }

  public String getHeader() {
    return header;
  }

  public int getLifetime() {
    return lifetime;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public void setLifetime(int lifetime) {
    this.lifetime = lifetime;
  }
}
