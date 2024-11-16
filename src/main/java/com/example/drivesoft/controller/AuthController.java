package com.example.drivesoft.controller;

import com.example.drivesoft.model.User;
import com.example.drivesoft.security.JwtUtil;
import com.example.drivesoft.security.LoginRequestDTO;
import com.example.drivesoft.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserDetailsServiceImpl userDetailsService;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;

  public AuthController(
      UserDetailsServiceImpl userDetailsService,
      JwtUtil jwtUtil,
      AuthenticationManager authenticationManager) {
    this.userDetailsService = userDetailsService;
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
  }

  @GetMapping("/welcome")
  public String welcome() {
    return "Welcome this endpoint is not secure";
  }

  @PostMapping("/addNewUser")
  public String addNewUser(@RequestBody User userInfo) {
    return userDetailsService.addUser(userInfo);
  }

  @PostMapping("/login")
  public String authenticateAndGetToken(@RequestBody LoginRequestDTO authRequest) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
    );
    if (authentication.isAuthenticated()) {
      return jwtUtil.generateToken(authRequest.getUsername());
    } else {
      throw new UsernameNotFoundException("Invalid user request!");
    }
  }

}
