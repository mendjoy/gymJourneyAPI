package io.github.mendjoy.gymJourneyAPI.dto.workout;

import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDetailsDto;

public record WorkoutExerciseDetailsDto(
        Long id,
        Long workoutSectionId,
        Integer sets,
        Integer repetitions,
        Double weight,
        Integer restTime,
        ExerciseDetailsDto exercise
) {}