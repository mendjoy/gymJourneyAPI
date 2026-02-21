package io.github.mendjoy.gymJourneyAPI.config.mapper;

import io.github.mendjoy.gymJourneyAPI.domain.WorkoutSectionExercise;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkoutExerciseMapper {

    @Mapping(source = "exercise", target = "exercise")
    WorkoutExerciseDetailsDto toDetailsDto(WorkoutSectionExercise entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workoutSection", ignore = true)
    @Mapping(target = "exercise", ignore = true)
    WorkoutSectionExercise toEntity(WorkoutExerciseDto dto);

}
