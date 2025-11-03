package io.github.mendjoy.gymJourneyAPI.dto.exercise;

import io.github.mendjoy.gymJourneyAPI.dto.muscleGroup.MuscleGroupDto;

import java.util.Set;

public record ExerciseDetailsDto(
        Long id,
        String name,
        String description,
        Set<MuscleGroupDto> muscleGroups
) {}