package io.github.mendjoy.gymJourneyAPI.dto.workout;

import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDto;

public record WorkoutExerciseDetailsDto(
        Long id,
        Integer sets,
        Integer repetitions,
        Double weight,
        Integer restTime,
        ExerciseDto exercise
) {}