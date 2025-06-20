package io.github.mendjoy.gymJourneyAPI.controller.user;

import io.github.mendjoy.gymJourneyAPI.dto.responseAPI.ResponseApiDTO;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserAuthDTO;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserLoginDTO;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserRegisterDTO;
import io.github.mendjoy.gymJourneyAPI.service.user.UserAuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private final UserAuthService userAuthService;
    private final AuthenticationManager authenticationManager;

    public UserAuthController(UserAuthService userAuthService, AuthenticationManager authenticationManager) {
        this.userAuthService = userAuthService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseApiDTO> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        UserAuthDTO userAuthDTO = userAuthService.authenticate(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        return ResponseEntity.ok(ResponseApiDTO.success(HttpStatus.OK, userAuthDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseApiDTO> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO){
        UserAuthDTO userAuthDTO = userAuthService.register(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseApiDTO.success(HttpStatus.CREATED, userAuthDTO));
    }
}
