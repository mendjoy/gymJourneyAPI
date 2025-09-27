package io.github.mendjoy.gymJourneyAPI.dto;

import io.github.mendjoy.gymJourneyAPI.domain.Exercise;

public record ExerciseDto(
        Integer id,
        String name,
        String description,
        String muscleGroup
) {
    public ExerciseDto(Exercise exercise) {
        this(exercise.getId(), exercise.getName(), exercise.getDescription(), exercise.getMuscleGroup());
    }
}

