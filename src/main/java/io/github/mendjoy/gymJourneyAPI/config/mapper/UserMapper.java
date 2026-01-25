package io.github.mendjoy.gymJourneyAPI.config.mapper;

import io.github.mendjoy.gymJourneyAPI.domain.Role;
import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.dto.role.RoleDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserRegisterDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserRegisterDto dto);

    default RoleDto roleToRoleDto(Role role) {
        if (role == null) return null;
        return new RoleDto(role.getName().name()); // Converte enum RoleName para String
    }

    default List<RoleDto> rolesToRoleDtos(List<Role> roles) {
        if (roles == null) return null;
        return roles.stream().map(this::roleToRoleDto).toList();
    }
}
