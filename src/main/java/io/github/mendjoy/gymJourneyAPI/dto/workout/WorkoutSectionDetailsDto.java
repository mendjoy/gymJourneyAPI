package io.github.mendjoy.gymJourneyAPI.dto.workout;

import java.util.List;

public record WorkoutSectionDetailsDto(
        Long id,
        Long workoutId,
        String name,
        String description,
        List<WorkoutExerciseDetailsDto> workoutExercises
) {}