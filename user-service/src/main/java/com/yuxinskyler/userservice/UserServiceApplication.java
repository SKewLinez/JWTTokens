package com.yuxinskyler.userservice;

import java.util.ArrayList;

import com.yuxinskyler.api.UserResource;
import com.yuxinskyler.userservice.domain.AppUser;
import com.yuxinskyler.userservice.domain.Role;
import com.yuxinskyler.userservice.service.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.yuxinskyler")
public class UserServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
	  return new BCryptPasswordEncoder();
  }

  @Bean
  CommandLineRunner run(UserService userService) {
	  return args -> {
		  userService.saveRole(new Role(1, "ROLE_USER"));
		  userService.saveRole(new Role(2, "ROLE_MANAGER"));
		  userService.saveRole(new Role(3, "ROLE_ADMIN"));
		  userService.saveRole(new Role(4, "ROLE_SUPER_ADMIN"));

		  userService.saveUser(new AppUser(1, "John Travolta", "john", "1234", new ArrayList<>()));
		  userService.saveUser(new AppUser(2, "Will Smith", "will", "1234", new ArrayList<>()));
		  userService.saveUser(new AppUser(3, "Jim Carry", "jim", "1234", new ArrayList<>()));
		  userService.saveUser(new AppUser(4, "Arnold Welsh", "arnold", "1234", new ArrayList<>()));

		  userService.addRoleToUser("john", "ROLE_USER");
		  userService.addRoleToUser("jim", "ROLE_MANAGER");
		  userService.addRoleToUser("will", "ROLE_ADMIN");
		  userService.addRoleToUser("arnold", "ROLE_SUPER_ADMIN");
		  userService.addRoleToUser("arnold", "ROLE_ADMIN");
		  userService.addRoleToUser("arnold", "ROLE_USER");


		  
		  

		  


		  

	  };
  }
}
