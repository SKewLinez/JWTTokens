package com.yuxinskyler.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.yuxinskyler.userservice.domain.AppUser;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;
import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class CustomAuthenticationFilter
  extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public CustomAuthenticationFilter(
    AuthenticationManager authenticationManager
  ) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    log.info("Username is: {}", username);
    log.info("Password is: {}", password);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
      username,
      password
    );
    return authenticationManager.authenticate(authenticationToken);
  }

  @Override
  protected void successfulAuthentication(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain chain,
    Authentication authResult
  )
    throws IOException {
    // Authentication authentication = SecurityContextHolder
    //   .getContext()
    //   .getAuthentication();
    User user = (User) authResult.getPrincipal();
    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
    String access_token = JWT
      .create()
      .withSubject(user.getUsername())
      .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
      .withIssuer(request.getRequestURL().toString())
      .withClaim(
        "roles",
        user
          .getAuthorities()
          .stream()
          .map(GrantedAuthority::getAuthority)
          .collect(Collectors.toList())
      )
      .sign(algorithm);
    String refresh_token = JWT
      .create()
      .withSubject(user.getUsername())
      .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
      .withIssuer(request.getRequestURL().toString())
      .withClaim(
        "roles",
        user
          .getAuthorities()
          .stream()
          .map(GrantedAuthority::getAuthority)
          .collect(Collectors.toList())
      )
      .sign(algorithm);
    response.setHeader("access_token", access_token);
    response.setHeader("refresh_token", refresh_token);
  }
}
