package io.github.mendjoy.gymJourneyAPI.dto.exercise;

import jakarta.validation.constraints.NotBlank;

public record ExerciseDto(

        Long id,

        @NotBlank(message = "O nome do exercicio deve ser informado!")
        String name,

        @NotBlank(message = "A descricao do exercicio deve ser informada!")
        String description,

        @NotBlank(message = "O grupo muscular deve ser informado!")
        String muscleGroup
) {}
