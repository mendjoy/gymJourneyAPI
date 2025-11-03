package io.github.mendjoy.gymJourneyAPI.dto.workout;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record WorkoutDto(

        Long id,

        @NotNull(message = "O id do usuário deve ser informado!")
        Long userId,

        @NotBlank(message = "O nome do treino deve ser informado!")
        String name,

        @NotBlank(message = "A descrição do treino deve ser informada!")
        String description,

        @NotNull(message = "O número de sessões do treino deve ser informado!")
        @Min(value = 1, message = "O número de sessões do treino deve ser maior que zero!")
        Integer maxSessions,

        @NotNull(message = "A data de início do treino deve ser informada!")
        LocalDate startDate,

        LocalDate endDate
) {}
