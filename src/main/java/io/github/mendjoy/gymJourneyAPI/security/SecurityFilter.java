package io.github.mendjoy.gymJourneyAPI.security;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import io.github.mendjoy.gymJourneyAPI.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recoverToker(request);

        if(token != null){
            String email = tokenService.verifyToken(token);
            User user = userRepository.findByEmailIgnoreCaseAndVerifiedTrueAndActiveTrue(email).orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado!"));

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToker(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null){
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }
}
