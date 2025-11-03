package io.github.mendjoy.gymJourneyAPI.dto.workout;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WorkoutSectionDto(

        Long id,

        @NotNull(message = "O id do treino deve ser informado!")
        Long workoutId,

        @NotBlank(message = "O nome da seção deve ser informado!")
        String name,

        @NotBlank(message = "A descrição deve ser informada!")
        String description

) {}
