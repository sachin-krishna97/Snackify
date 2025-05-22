package com.snackify.security;

import com.snackify.model.User;
import io.jsonwebtoken.Claims;
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

  public String generateToken(User user) {
    System.out.println("Generated JWT for: " + user.getEmail() + " with role: " + user.getRole());

    return Jwts.builder()
        .setSubject(user.getEmail()) // still use email as subject
        .claim("role", user.getRole().name()) // 🔐 include role inside token
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5))
        .signWith(getSigningKey())
        .compact();
  }

  // ✅ Extract username (email) from token
  public String extractUsername(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey()) // validate the token with the same secret
        .build()
        .parseClaimsJws(token) // parse it
        .getBody()
        .getSubject(); // get the "subject" (in our case, the username/email)
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public String extractRole(String token) {
    return extractAllClaims(token).get("role", String.class);
  }
}
