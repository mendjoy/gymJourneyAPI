package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.dto.TokenDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserLoginDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public TokenDto login(UserLoginDto userLoginDto){

        try {

            UsernamePasswordAuthenticationToken tokenAuth = new UsernamePasswordAuthenticationToken(userLoginDto.email(), userLoginDto.password());
            Authentication auth = authenticationManager.authenticate(tokenAuth);
            String newToken = tokenService.generateToken((User) auth.getPrincipal());
            return new TokenDto(newToken);

        }catch (BadCredentialsException e){

            throw GymJourneyException.forbidden("Email ou senha inválidos!");
            
        }
    }

}
