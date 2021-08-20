package com.yuxinskyler.api;

import com.yuxinskyler.userservice.domain.AppUser;
import com.yuxinskyler.userservice.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {

  private final UserService userService;

  @GetMapping("/users")
  public ResponseEntity<List<AppUser>> getUsers() {
    return ResponseEntity.ok().body(userService.getUsers());
  }
// public String say() {
//     return "hello";
// }
}
