package io.github.mendjoy.gymJourneyAPI.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.dto.response.ApiResponseDto;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import io.github.mendjoy.gymJourneyAPI.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.equals("/auth/login")  || path.equals("/users")  || path.equals("/users/verify");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, GymJourneyException {

        try {

            String token = recoverToken(request);

            if (token != null) {
                String email = tokenService.verifyToken(token);
                User user = (User) userRepository.findByEmail(email).orElse(null);

                if (user != null && user.getVerified()) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities()
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            filterChain.doFilter(request, response);

        } catch (GymJourneyException ex) {

            handleException(response, ex.getMessage(), ex.getHttpStatus());

        } catch (Exception ex) {

            handleException(response, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }

    private void handleException(HttpServletResponse response, String message, HttpStatus httpStatus) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ApiResponseDto dto = new ApiResponseDto(response.getStatus(), message);
        String json = objectMapper.writeValueAsString(dto);
        response.getWriter().write(json);
    }
}
