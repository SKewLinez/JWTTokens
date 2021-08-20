package com.yuxinskyler.userservice.service;

import com.yuxinskyler.userservice.domain.AppUser;
import com.yuxinskyler.userservice.domain.Role;
import com.yuxinskyler.userservice.repo.RoleRepo;
import com.yuxinskyler.userservice.repo.UserRepo;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepo userRepo;
  private final RoleRepo roleRepo;

  @Override
  public AppUser saveUser(AppUser user) {
    log.info("Saving new user {} to the database", user);
    return userRepo.save(user);
  }

  @Override
  public Role saveRole(Role role) {
    log.info("Saving new role {} to the database", role);
    return roleRepo.save(role);
  }

  @Override
  public void addRoleToUser(String username, String roleName) {
    log.info("Adding role {} to user {}", roleName, username);
    AppUser user = userRepo.findByUsername(username);
    Role role = roleRepo.findByName(roleName);
    user.getRoles().add(role);
  }

  @Override
  public AppUser getUser(String username) {
    log.info("Fetching user {}", username);
    return userRepo.findByUsername(username);
  }

  @Override
  public List<AppUser> getUsers() {
    log.info("Fetching all users");
    return userRepo.findAll();
  }
}
