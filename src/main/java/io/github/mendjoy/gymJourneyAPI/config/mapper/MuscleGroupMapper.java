package io.github.mendjoy.gymJourneyAPI.config.mapper;

import io.github.mendjoy.gymJourneyAPI.domain.MuscleGroup;
import io.github.mendjoy.gymJourneyAPI.dto.muscleGroup.MuscleGroupDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MuscleGroupMapper {

    MuscleGroupDto toDto(MuscleGroup muscleGroup);

    MuscleGroup toEntity(MuscleGroupDto dto);
}
