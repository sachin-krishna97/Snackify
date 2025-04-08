package com.snackify.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  private static final String SECRET_KEY =
      "mySuperStrongSecretKeyThatIsAtLeast32CharsLong123!"; // you can change this!

  // Create a Key object from the secret string
  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  public String generateToken(String username) {
    System.out.println("Generated JWT username: " + username);
    return Jwts.builder()
        .setSubject(username) // who the token is for
        .setIssuedAt(new Date()) // when it was created
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5)) // valid 5 hrs
        .signWith(getSigningKey()) // use the Key object
        .compact();
  }
}
