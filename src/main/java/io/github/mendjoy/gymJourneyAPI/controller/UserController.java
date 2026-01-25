package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.dto.response.ApiResponseDto;
import io.github.mendjoy.gymJourneyAPI.dto.role.RoleDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserPasswordDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserRegisterDto;
import io.github.mendjoy.gymJourneyAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        UserDto userDto = userService.register(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> update(
            @Valid @RequestBody UserDto userDto,
            @AuthenticationPrincipal User authenticatedUser) {
        UserDto userUpdated = userService.update(userDto, authenticatedUser);
        return ResponseEntity.ok(userUpdated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/add-role/{id}")
    public ResponseEntity<ApiResponseDto> addRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleDto roleDto) {
        userService.addRole(id, roleDto);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Papel de usuário inserido com sucesso"
        ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/remove-role/{id}")
    public ResponseEntity<ApiResponseDto> removeRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleDto roleDto) {
        userService.removeRole(id, roleDto);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Papel de usuário removido com sucesso"
        ));
    }

    @PatchMapping("/disable/{id}")
    public ResponseEntity<ApiResponseDto> disable(
            @PathVariable Long id,
            @AuthenticationPrincipal User authenticatedUser) {
        userService.disable(id, authenticatedUser);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Usuário inativado com sucesso"
        ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/enable/{id}")
    public ResponseEntity<ApiResponseDto> enable(@PathVariable Long id) {
        userService.enable(id);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Usuário ativado com sucesso"
        ));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ApiResponseDto> changePassword(
            @Valid @RequestBody UserPasswordDto userPasswordDto,
            @AuthenticationPrincipal User authenticatedUser) {
        userService.changePassword(userPasswordDto, authenticatedUser);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Senha alterada com sucesso"
        ));
    }

    @GetMapping("/verify-account")
    public ResponseEntity<ApiResponseDto> verifyAccount(@RequestParam String token) {
        userService.verifyEmail(token);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Conta verificada com sucesso!"
        ));
    }

    @GetMapping
    public ResponseEntity<UserDto> getAuthenticatedUser(
            @AuthenticationPrincipal User authenticatedUser) {
        UserDto userDto = userService.getAuthenticatedUser(authenticatedUser);
        return ResponseEntity.ok(userDto);
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        UserDto userDto = userService.getById(id);
        return ResponseEntity.ok(userDto);
    }
}