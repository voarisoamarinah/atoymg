package org.atoy.atoymg.config.security.filters;

import org.atoy.atoymg.config.security.handlers.JwtAccessDeniedHandler;
import org.atoy.atoymg.config.security.handlers.JwtAuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Password encoding
  public final UserDetailsService userDetailsService;
  private final JwtAuthenticationFilter authFilter;
  
  //NEW
  private final JwtAuthEntryPoint authenticationEntryPoint;
  private final JwtAccessDeniedHandler accessDeniedHandler;
  
  @Autowired
  public SecurityConfig(JwtAuthenticationFilter authFilter, UserDetailsService userDetailsService,
    JwtAuthEntryPoint authenticationEntryPoint,
    JwtAccessDeniedHandler accessDeniedHandler) {
    this.authFilter = authFilter;
    this.userDetailsService = userDetailsService;
    
    this.authenticationEntryPoint = authenticationEntryPoint;
    this.accessDeniedHandler = accessDeniedHandler;
  }

  static CorsConfigurationSource getCorsConfigurationSource(CorsConfiguration configuration) {
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(auth -> auth
          .requestMatchers("/auth/**", "/welcome/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
          // Autoriser ADMIN sur TOUTES les routes via une expression SpEL
          .anyRequest().access(new WebExpressionAuthorizationManager(
              "hasAuthority('ROLE_ADMIN')" 
          ))
      )
      .sessionManagement(sess -> sess
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions
      )
      .authenticationProvider(authenticationProvider()) // Custom authentication provider
      .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
      .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler);
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*")); // Spécifiez explicitement l'origine
    return getCorsConfigurationSource(configuration);
  }

  @Bean
    public PasswordEncoder passwordEncoder() {
    return passwordEncoder; // Password encoding
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    return authenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}
