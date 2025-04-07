package com.snackify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class SnackifyApplication {

  public static void main(String[] args) {
    SpringApplication.run(SnackifyApplication.class, args);
  }
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable()) // we don't need form-based protection
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/api/auth/**",         // allow register/login
                            "/swagger-ui/**",       // allow swagger
                            "/v3/api-docs/**"       // allow docs
                    ).permitAll()               // these paths don't need login
                    .anyRequest().authenticated() // other things need login
            )
            .formLogin(form -> form.disable())       // 🚫 no login page
            .httpBasic(httpBasic -> httpBasic.disable()); // 🚫 no popup

    return http.build();
  }

}
