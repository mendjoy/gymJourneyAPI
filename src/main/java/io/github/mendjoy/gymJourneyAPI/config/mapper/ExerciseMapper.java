package io.github.mendjoy.gymJourneyAPI.config.mapper;


import io.github.mendjoy.gymJourneyAPI.domain.Exercise;
import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MuscleGroupMapper.class)
public interface ExerciseMapper {

    ExerciseDto toDto(Exercise exercise);

    ExerciseDetailsDto toDetailsDto(Exercise exercise);

    Exercise toEntity(ExerciseDto dto);
}
