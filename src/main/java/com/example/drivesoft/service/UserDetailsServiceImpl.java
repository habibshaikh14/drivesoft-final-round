package com.example.drivesoft.service;

import com.example.drivesoft.model.User;
import com.example.drivesoft.repository.UserRepository;
import com.example.drivesoft.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserRepository repository;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public void injectDependencies(UserRepository repository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userDetail = repository.findByUsername(username); // Assuming 'email' is used as username

    // Converting User to UserDetails
    return userDetail.map(UserDetailsImpl::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
  }

  public String addUser(User userInfo) {
    // Encode password before saving the user
    userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
    repository.save(userInfo);
    return "User Added Successfully";
  }

}
