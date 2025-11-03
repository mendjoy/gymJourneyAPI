package io.github.mendjoy.gymJourneyAPI.config.mapper;

import io.github.mendjoy.gymJourneyAPI.domain.Workout;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {WorkoutSectionMapper.class})
public interface WorkoutMapper {

    WorkoutDto toDto(Workout workout);

    @Mapping(source = "user.id", target = "userId")
    WorkoutDetailsDto toDetailsDto(Workout entity);

    Workout toEntity(WorkoutDto dto);
}
