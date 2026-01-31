package io.github.mendjoy.gymJourneyAPI.config.mapper;

import io.github.mendjoy.gymJourneyAPI.domain.WorkoutSectionExercise;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkoutExerciseMapper {

    @Mapping(source = "workoutSection.id", target = "workoutSectionId")
    @Mapping(source = "exercise.id", target = "exerciseId")
    WorkoutExerciseDto toDto(WorkoutSectionExercise entity);

    @Mapping(source = "exercise", target = "exercise")
    WorkoutExerciseDetailsDto toDetailsDto(WorkoutSectionExercise entity);

    WorkoutSectionExercise toEntity(WorkoutExerciseDto dto);
}
