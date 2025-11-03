package io.github.mendjoy.gymJourneyAPI.mapper;

import io.github.mendjoy.gymJourneyAPI.domain.WorkoutSection;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {WorkoutExerciseMapper.class})
public interface WorkoutSectionMapper {

    @Mapping(source = "workout.id", target = "workoutId")
    WorkoutSectionDto toDto(WorkoutSection entity);

    @Mapping(source = "workout.id", target = "workoutId")
    WorkoutSectionDetailsDto toDetailsDto(WorkoutSection entity);

    WorkoutSection toEntity(WorkoutSectionDto dto);
}
