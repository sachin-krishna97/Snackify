package com.snackify;

import com.snackify.security.JwtAuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class SnackifyApplication {

  private final JwtAuthFilter jwtAuthFilter;

  public SnackifyApplication(JwtAuthFilter jwtAuthFilter) {
    this.jwtAuthFilter = jwtAuthFilter;
  }

  public static void main(String[] args) {
    SpringApplication.run(SnackifyApplication.class, args);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/error")
                    .permitAll()
                    .requestMatchers("/api/products/**")
                    .hasAnyRole("USER", "ADMIN") // accessible to USER & ADMIN
                    .requestMatchers("/api/admin/**")
                    .hasRole("ADMIN") // only ADMIN
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .formLogin(form -> form.disable())
        .httpBasic(httpBasic -> httpBasic.disable());

    return http.build();
  }
}
