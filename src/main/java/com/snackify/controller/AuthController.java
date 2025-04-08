package com.snackify.controller;

import com.snackify.dto.LoginRequest;
import com.snackify.dto.RegisterRequest;
import com.snackify.model.User;
import com.snackify.repository.UserRepository;
import com.snackify.security.JwtService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthController(UserRepository userRepository, JwtService jwtService) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  @PostMapping("/register")
  public String register(@RequestBody RegisterRequest request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      return "Email already in use!";
    }

    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userRepository.save(user);
    return "User registered successfully!";
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail()).orElse(null);

    if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    // 🔐 Generate JWT Token
    String token = jwtService.generateToken(user.getEmail());

    // ✅ Print it in console for debug
    System.out.println("Generated JWT Token: " + token);

    // 📦 Return the token in JSON format
    return ResponseEntity.ok().body(Map.of("token", token));
  }
}
