package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserRegisterRequestDto;
import io.github.mendjoy.gymJourneyAPI.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserRegisterRequestDto userRegisterRequestDto){
        UserDto userDto = userService.registerUser(userRegisterRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @GetMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String token){
        userService.verifyEmail(token);
        return ResponseEntity.ok("Conta verificada com sucesso!");
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto){
        UserDto userDto = userService.registerUser(userRegisterRequestDto);
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<String> disableUser(@PathVariable Integer id, @AuthenticationPrincipal User authenticatedUser) {
        userService.disableUser(id, authenticatedUser);
        return ResponseEntity.ok("Usuário inativado com sucesso.");
    }

}
