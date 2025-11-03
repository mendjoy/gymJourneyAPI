package io.github.mendjoy.gymJourneyAPI.dto.workout;

import java.time.LocalDate;
import java.util.List;

public record WorkoutDetailsDto(
        Integer id,
        Integer userId,
        String name,
        String description,
        Integer maxSessions,
        LocalDate startDate,
        LocalDate endDate,
        List<WorkoutSectionDetailsDto> workoutSections
) {}