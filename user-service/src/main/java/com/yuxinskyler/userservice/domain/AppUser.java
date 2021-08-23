package com.yuxinskyler.userservice.domain;

import static javax.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.*;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.beans.factory.annotation.Autowire;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

  @Id
  @GeneratedValue(strategy = AUTO)
  private long id;
  private String name;
  private String username;
  private String password;
  @ManyToMany(fetch = EAGER)
  private Collection<Role> roles = new ArrayList<>();
}
