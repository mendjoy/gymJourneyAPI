package io.github.mendjoy.gymJourneyAPI.dto.user;


import io.github.mendjoy.gymJourneyAPI.dto.role.RoleDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record UserDto(
        Long id,
        String email,

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String name,
        String phone,
        LocalDate birthDate,
        List<RoleDto> roles
) {}