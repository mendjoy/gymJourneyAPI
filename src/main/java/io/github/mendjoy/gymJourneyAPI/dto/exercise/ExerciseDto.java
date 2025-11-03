package io.github.mendjoy.gymJourneyAPI.dto.exercise;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ExerciseDto(

        Long id,

        @NotBlank(message = "O nome do exercicio deve ser informado!")
        String name,

        @NotBlank(message = "A descricao do exercicio deve ser informada!")
        String description,

        @NotNull(message = "Os grupos musculares devem ser informados!")
        @NotEmpty(message = "O exercício deve ter pelo menos um grupo muscular!")
        Set<Long> muscleGroupIds
) {}
