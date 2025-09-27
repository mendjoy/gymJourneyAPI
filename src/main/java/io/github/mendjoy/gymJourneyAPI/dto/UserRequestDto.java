package io.github.mendjoy.gymJourneyAPI.dto;

import io.github.mendjoy.gymJourneyAPI.domain.User;

public record UserDto(Integer id,
                      String email,
                      String name) {

    public UserDto (User user){
        this(user.getId(), user.getEmail(), user.getName());
    }
}
