package com.springboot.blog.security;

import com.springboot.blog.model.Role;
import com.springboot.blog.model.User;
import com.springboot.blog.repository.UserRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author Barnaba Mutai
*Created on Saturday, July , 16, 2022
*/
@SpringBootApplication
@ComponentScan("com")
public class CustomUserDetailsService implements UserDetailsService {
  private UserRepository userRepository;
  public CustomUserDetailsService(UserRepository userRepository){
    this.userRepository=userRepository;
  }
  @Override
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    User user= userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail)
      .orElseThrow(()->new UsernameNotFoundException("User Not Found with username or email: "+usernameOrEmail));
    return new org.springframework.security.core.userdetails.User(user.getEmail(),
      user.getPassword(),mapRolesToAutorities(user.getRoles()));
  }
  private Collection<? extends GrantedAuthority>  mapRolesToAutorities(Set<Role> roles){
   return  roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
  }
}
