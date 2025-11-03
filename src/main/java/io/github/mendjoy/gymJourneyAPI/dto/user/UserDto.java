package io.github.mendjoy.gymJourneyAPI.dto.user;


import io.github.mendjoy.gymJourneyAPI.dto.role.RoleDto;

import java.time.LocalDate;
import java.util.List;

public record UserDto(
        Long id,
        String email,
        String name,
        String phone,
        LocalDate birthDate,
        List<RoleDto> roles
) {}