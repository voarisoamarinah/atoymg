package org.atoy.atoymg.config.security.services;

import org.atoy.atoymg.config.security.models.AuthRequest;
import org.atoy.atoymg.config.security.models.UserInfoDetails;
import org.atoy.atoymg.config.security.models.UserUpdateRequest;
import org.atoy.atoymg.models.UserEntity;
import org.atoy.atoymg.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.atoy.atoymg.config.security.filters.SecurityConfig.passwordEncoder;

@Service
public class UserInfoService implements UserDetailsService {
  private final UserEntityRepository repository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  
  @Autowired
  public UserInfoService(UserEntityRepository repository, JwtService jwtService, @Lazy AuthenticationManager authenticationManager) {
    this.repository = repository;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserEntity> userDetail = repository.findAllByName(username); // Assuming 'email' is used as username
  
    // Converting UserInfo to UserDetails
    return userDetail.map(UserInfoDetails::new)
      .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
  }

  public String addUser(UserEntity user) {
      // Encode password before saving the user
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      repository.save(user);
      return "User Added Successfully";
  }
  
  public String authenticateAndGetToken(AuthRequest authRequest) {
    System.out.println("Received username: " + authRequest.username());
    System.out.println("Received password: " + authRequest.password());

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
    );
    if (authentication.isAuthenticated()) {
        return jwtService.generateToken(authRequest.username());
    } else {
        throw new UsernameNotFoundException("Invalid user request!");
    }
  }

  public String refreshAccessToken(String oldToken) {
      // 1. extrait l’identité
      String username = jwtService.extractUsername(oldToken);
      if (username == null) throw new IllegalArgumentException("Invalid token");
  
      // 2. charge l’utilisateur
      UserDetails user = loadUserByUsername(username);
  
      // 3. valide encore la signature / expiration
      if (!jwtService.validateToken(oldToken, user))
      throw new IllegalArgumentException("Token validation failed");
  
      // 5. génère un nouveau
      return jwtService.generateToken(username);
  }
  
  // Ajoutez ces méthodes dans UserInfoService
  public void updateUser(String username, UserUpdateRequest updateRequest) {
    UserEntity user = repository.findUserByName(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found: " + username);
    }
    
    if (updateRequest.getName() != null) {
        user.setName(updateRequest.getName());
    }
    if (updateRequest.getEmail() != null) {
        user.setEmail(updateRequest.getEmail());
    }
        
    repository.save(user);
  }

  public void changeUserPassword(
          String username, 
          String oldPassword, 
          String newPassword, 
          String confirmPassword) {
    
    if (!newPassword.equals(confirmPassword)) {
        throw new IllegalArgumentException("New password and confirmation do not match");
    }
  
    UserEntity user = repository.findUserByName(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found: " + username);
    }
        
    // Vérifier l'ancien mot de passe
    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
      throw new IllegalArgumentException("Old password is incorrect");
    }
  
    user.setPassword(passwordEncoder.encode(newPassword));
    repository.save(user);
  }
}
