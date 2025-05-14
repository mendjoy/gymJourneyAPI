package io.github.mendjoy.gymJourneyAPI.security;

import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import io.github.mendjoy.gymJourneyAPI.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityConfig(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {
        SecurityFilter securityFilter = new SecurityFilter(tokenService, userRepository);

        return http.csrf(AbstractHttpConfigurer::disable)
                   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .authorizeHttpRequests(authorize -> {
                        authorize.requestMatchers("/auth/login", "/auth/register").permitAll();
                        authorize.requestMatchers("/users/{id}/grant-admin").hasAuthority("ROLE_ADMIN");
                        authorize.requestMatchers("/exercises/**").hasAuthority("ROLE_ADMIN");
                        authorize.requestMatchers(HttpMethod.POST, "/workout/**").hasAuthority("ROLE_ADMIN");
                        authorize.requestMatchers(HttpMethod.DELETE, "/workout/**").hasAuthority("ROLE_ADMIN");
                        authorize.requestMatchers("/workout/all/{userId}").hasAuthority("ROLE_ADMIN");
                        authorize.requestMatchers("/workout/current/{userId}").hasAuthority("ROLE_ADMIN");
                        authorize.anyRequest().authenticated();
                   })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
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

}
