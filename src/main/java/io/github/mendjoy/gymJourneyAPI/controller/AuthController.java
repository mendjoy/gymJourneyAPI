package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.dto.UserLoginDto;
import io.github.mendjoy.gymJourneyAPI.dto.UserTokenDto;
import io.github.mendjoy.gymJourneyAPI.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> login(@Valid @RequestBody UserLoginDto userLoginDto){
        UserTokenDto userTokenDto = authService.login(userLoginDto);
        return ResponseEntity.ok(userTokenDto);
    }
}
