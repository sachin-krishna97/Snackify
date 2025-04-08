package com.snackify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class SnackifyApplication {

  public static void main(String[] args) {
    SpringApplication.run(SnackifyApplication.class, args);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/api/auth/**", // public auth endpoints
                        "/swagger-ui/**", // Swagger UI
                        "/swagger-ui.html", // base Swagger HTML
                        "/v3/api-docs/**", // OpenAPI docs
                        "/v3/api-docs", // fallback OpenAPI
                        "/error" // ✅ allow Spring Boot's error endpoint
                        )
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .formLogin(form -> form.disable())
        .httpBasic(httpBasic -> httpBasic.disable());

    return http.build();
  }
}
