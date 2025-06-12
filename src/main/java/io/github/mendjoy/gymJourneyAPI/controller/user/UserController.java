package io.github.mendjoy.gymJourneyAPI.controller.user;

import io.github.mendjoy.gymJourneyAPI.dto.responseAPI.ResponseApiDTO;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserPasswordUpdateDTO;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserUpdateDTO;
import io.github.mendjoy.gymJourneyAPI.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/{id}/grant-admin")
    public ResponseEntity<ResponseApiDTO> grantAdmin(@PathVariable Integer id){
        userService.grantAdminRole(id);
        return ResponseEntity.ok(ResponseApiDTO.success(HttpStatus.OK, "Usuário promovido a ADMIN com sucesso!"));
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<ResponseApiDTO> updateUser(@PathVariable Integer id,@RequestBody UserUpdateDTO userUpdateDTO){
        userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(ResponseApiDTO.success(HttpStatus.OK, "Dados alterados com sucesso!"));
    }

    @PatchMapping("/{id}/update/password")
    public ResponseEntity<ResponseApiDTO> updatePassword(@PathVariable Integer id, @Valid @RequestBody UserPasswordUpdateDTO userPasswordUpdateDTO){
        userService.updatePassword(id, userPasswordUpdateDTO);
        return ResponseEntity.ok(ResponseApiDTO.success(HttpStatus.OK, "Senha alterada com sucesso!"));
    }

}
