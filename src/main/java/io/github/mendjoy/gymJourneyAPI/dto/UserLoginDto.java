package io.github.mendjoy.gymJourneyAPI.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLogin(@NotBlank String email, @NotBlank String password) {
    
}
