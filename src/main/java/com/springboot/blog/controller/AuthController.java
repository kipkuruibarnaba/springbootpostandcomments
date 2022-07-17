package com.springboot.blog.controller;

import com.springboot.blog.model.Role;
import com.springboot.blog.model.User;
import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author Barnaba Mutai
 * Created on Sunday, July , 17, 2022
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JwtTokenProvider tokenProvider;
  @PostMapping("/signin")
  public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginDto.getUsernameOrEmail(),loginDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    //get Token from token provider
    String token = tokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JWTAuthResponse(token));
  }
//  public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
//   Authentication authentication = authenticationManager.authenticate(
//      new UsernamePasswordAuthenticationToken(
//        loginDto.getUsernameOrEmail(),loginDto.getPassword()));
//    SecurityContextHolder.getContext().setAuthentication(authentication);
//    return new ResponseEntity<>("User Signed in Successfully", HttpStatus.OK);
//  }
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
    //check for Username exists in the database
     if(userRepository.existsByUsername(signUpDto.getUsername())){
       return new ResponseEntity<>("User name already exists in the database ",HttpStatus.BAD_REQUEST);
     };
    //check for Email exists in the database
    if(userRepository.existsByEmail(signUpDto.getEmail())){
      return new ResponseEntity<>("Email already exists in the database ",HttpStatus.BAD_REQUEST);
    };

    //Create user object
    User user = new User();
    user.setName(signUpDto.getName());
    user.setUsername(signUpDto.getUsername());
    user.setEmail(signUpDto.getEmail());
    user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

    Role roles = roleRepository.findByName("ROLE_ADMIN").get();
    user.setRoles(Collections.singleton(roles));
    System.out.println("====================Registered User Details=============== "+user);
    userRepository.save(user);
    return new ResponseEntity<>("User Successfully registered ",HttpStatus.OK);
  }
}
