package com.yuxinskyler.userservice.service;

import com.yuxinskyler.userservice.domain.AppUser;
import com.yuxinskyler.userservice.domain.Role;
import java.util.List;

public interface UserService {
  AppUser saveUser(AppUser user);
  Role saveRole(Role role);
  void addRoleToUser(String username, String roleName);
  void deleteUser(String username);
  AppUser getUser(String username);

  List<AppUser> getUsers(); // this not a good practice - please partition into pages
}
