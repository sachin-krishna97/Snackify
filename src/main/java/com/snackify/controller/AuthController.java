package com.snackify.controller;

import com.snackify.dto.LoginRequest;
import com.snackify.dto.RegisterRequest;
import com.snackify.model.Role;
import com.snackify.model.User;
import com.snackify.repository.UserRepository;
import com.snackify.security.JwtService;
import com.snackify.service.MailService;
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
  private final MailService mailService;

  public AuthController(
      UserRepository userRepository, JwtService jwtService, MailService MailService) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = new BCryptPasswordEncoder();
    this.mailService = MailService;
  }

  @PostMapping("/register")
  public String register(@RequestBody RegisterRequest request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      return "Email already in use!";
    }

    User user = new User(); // ✅ create the user first
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    // ✅ assign ADMIN if email matches, else USER
    if (request.getEmail().equals("admin@snackify.com")) {
      user.setRole(Role.ADMIN);
    } else {
      user.setRole(Role.USER);
    }

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
    String token = jwtService.generateToken(user);

    // ✅ Print it in console for debug
    System.out.println("Generated JWT Token: " + token);

    // 📦 Return the token in JSON format
    return ResponseEntity.ok().body(Map.of("token", token));
  }

  @PostMapping("/send-otp")
  public ResponseEntity<String> sendOtp(@RequestParam String email) {
    String otp = mailService.generateOtp();

    try {
      mailService.sendOtpEmail(email, otp);
      return ResponseEntity.ok("OTP sent to " + email);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to send OTP: " + e.getMessage());
    }
  }
}
