package io.github.mendjoy.gymJourneyAPI.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                   .sessionManagement( sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                   .authorizeHttpRequests(req -> {
                             req.requestMatchers("/users/register", "/users/verify-account", "/auth/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("ADMIN", "TRAINER")
                                .requestMatchers(HttpMethod.PATCH, "/users/add-role/{id}", "users/remove-role/{id}").hasRole("ADMIN")
                                .requestMatchers("/exercises/**").hasAnyRole("ADMIN", "TRAINER")
                                .requestMatchers(HttpMethod.POST, "/workouts/**").hasAnyRole("ADMIN", "TRAINER")
                                .requestMatchers(HttpMethod.PUT, "/workouts/**").hasAnyRole("ADMIN", "TRAINER")
                                .requestMatchers(HttpMethod.DELETE, "/workouts/**").hasAnyRole("ADMIN", "TRAINER")
                                .requestMatchers(HttpMethod.PUT, "/workout-sections/**").hasAnyRole("ADMIN", "TRAINER")
                                .requestMatchers(HttpMethod.DELETE, "/workout-sections/**").hasAnyRole("ADMIN", "TRAINER")
                                     .anyRequest().authenticated();
                   })
                   .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy(){
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("TRAINER")
                .role("TRAINER").implies("USER")
                .build();
    }
}
