package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.dto.response.ApiResponseDto;
import io.github.mendjoy.gymJourneyAPI.dto.role.RoleDto;
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

    @GetMapping
    public ResponseEntity<UserDto> getAuthenticatedUser(@AuthenticationPrincipal User authenticatedUser){
        UserDto userDto = userService.getAuthenticatedUser(authenticatedUser);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
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

    @PatchMapping("add-role/{id}")
    public ResponseEntity<ApiResponseDto> addRole(@PathVariable Long id, @RequestBody RoleDto roleDto){
        userService.addRole(id, roleDto);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK.value(), "Papel de usuario inserido com sucesso"));
    }

    @PatchMapping("remove-role/{id}")
    public ResponseEntity<ApiResponseDto> removeRole(@PathVariable Long id, @RequestBody RoleDto roleDto){
        userService.removeRole(id, roleDto);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK.value(), "Papel de usuario removido com sucesso"));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<String> disableUser(@PathVariable Long id, @AuthenticationPrincipal User authenticatedUser) {
        userService.disableUser(id, authenticatedUser);
        return ResponseEntity.ok("Usuário inativado com sucesso.");
    }

}
