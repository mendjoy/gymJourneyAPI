package io.github.mendjoy.gymJourneyAPI.controller.user;

import io.github.mendjoy.gymJourneyAPI.dto.responseAPI.ResponseApiDTO;
import io.github.mendjoy.gymJourneyAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PatchMapping("/{id}/grant-admin")
    public ResponseEntity<ResponseApiDTO> grantAdmin(@PathVariable Integer id){
        userService.grantAdminRole(id);
        return ResponseEntity.ok(ResponseApiDTO.success(HttpStatus.OK, "Usuário promovido a ADMIN com sucesso!"));
    }
}
