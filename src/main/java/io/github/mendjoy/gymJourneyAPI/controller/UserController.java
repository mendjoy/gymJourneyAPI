package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.dto.TokenDto;
import io.github.mendjoy.gymJourneyAPI.dto.response.ApiResponseDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserPasswordDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserRegisterDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserStatusDto;
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

    @PostMapping
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        UserDto userDto = userService.register(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> update(
            @PathVariable Long userId,
            @Valid @RequestBody UserDto userDto,
            @AuthenticationPrincipal User authenticatedUser) {
        UserDto userUpdated = userService.update(userId, userDto, authenticatedUser);
        return ResponseEntity.ok(userUpdated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<ApiResponseDto> addRole(
            @PathVariable Long userId,
            @PathVariable Long roleId) {
        userService.addRole(userId, roleId);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Papel de usuário inserido com sucesso"
        ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<ApiResponseDto> removeRole(
            @PathVariable Long userId,
            @PathVariable Long roleId) {
        userService.removeRole(userId, roleId);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Papel de usuário removido com sucesso"
        ));
    }

    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    @PatchMapping("/{userId}/status")
    public ResponseEntity<ApiResponseDto> changeStatus(
            @PathVariable Long userId,
            @RequestBody UserStatusDto userStatusDto,
            @AuthenticationPrincipal User authenticatedUser) {
        userService.changeStatus(userId, userStatusDto, authenticatedUser);
        String msg = userStatusDto.enabled() ?
                "Usuário ativado com sucesso" :
                "Usuário inativado com sucesso";
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                msg));
    }

    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    @PatchMapping("/{userId}/password")
    public ResponseEntity<ApiResponseDto> changePassword(
            @PathVariable Long userId,
            @Valid @RequestBody UserPasswordDto userPasswordDto,
            @AuthenticationPrincipal User authenticatedUser) {
        userService.changePassword(userId, userPasswordDto, authenticatedUser);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Senha alterada com sucesso"
        ));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponseDto> verifyEmail(@RequestBody TokenDto tokenDto) {
        userService.verifyEmail(tokenDto);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Conta verificada com sucesso!"
        ));
    }

    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getById(
            @PathVariable Long userId,
            @AuthenticationPrincipal User authenticatedUser) {
        UserDto userDto = userService.getById(userId, authenticatedUser);
        return ResponseEntity.ok(userDto);
    }
}