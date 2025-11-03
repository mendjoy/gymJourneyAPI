package io.github.mendjoy.gymJourneyAPI.dto.workout;

import jakarta.validation.constraints.NotNull;

public record WorkoutExerciseDto(
        Long id,

        @NotNull(message = "O id da secao deve ser informado!")
        Long workoutSectionId,

        @NotNull(message = "O id do exercicio deve ser informado!")
        Long exerciseId,

        @NotNull(message = "O numero de series deve ser informado!")
        Integer sets,

        @NotNull(message = "O numero de repetições deve ser informado!")
        Integer repetitions,

        Double weight,

        @NotNull(message = "O tempo de descanso deve ser informado!")
        Integer restTime
) {}
