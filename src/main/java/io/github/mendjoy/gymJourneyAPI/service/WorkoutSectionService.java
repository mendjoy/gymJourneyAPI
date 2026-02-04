package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.config.mapper.WorkoutSectionMapper;
import io.github.mendjoy.gymJourneyAPI.domain.Workout;
import io.github.mendjoy.gymJourneyAPI.domain.WorkoutSection;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDto;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutRepository;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutSectionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutSectionService {

    private final WorkoutRepository workoutRepository;
    private final WorkoutSectionRepository workoutSectionRepository;
    private final WorkoutSectionMapper workoutSectionMapper;

    public WorkoutSectionService(
            WorkoutRepository workoutRepository,
            WorkoutSectionRepository workoutSectionRepository,
            WorkoutSectionMapper workoutSectionMapper) {

        this.workoutRepository = workoutRepository;
        this.workoutSectionRepository = workoutSectionRepository;
        this.workoutSectionMapper = workoutSectionMapper;
    }

    public List<WorkoutSectionDto> create(Long workoutId,
                                          List<WorkoutSectionDto> sectionDtos) {

        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() ->
                        GymJourneyException.notFound("Treino não encontrado!")
                );

        List<WorkoutSection> sections = sectionDtos.stream()
                .map(dto -> {
                    WorkoutSection section = workoutSectionMapper.toEntity(dto);
                    section.setWorkout(workout);
                    return section;
                })
                .toList();

        return workoutSectionRepository.saveAll(sections)
                .stream()
                .map(workoutSectionMapper::toDto)
                .toList();
    }

    public WorkoutSectionDto update(Long workoutId,
                                    Long sectionId,
                                    WorkoutSectionDto dto) {

        WorkoutSection section = workoutSectionRepository.findById(sectionId)
                .orElseThrow(() ->
                        GymJourneyException.notFound("Seção de treino não encontrada!")
                );

        section.update(dto);

        return workoutSectionMapper.toDto(
                workoutSectionRepository.save(section)
        );
    }

    public Page<WorkoutSectionDetailsDto> findByWorkoutId(Long workoutId,
                                                          int page,
                                                          int size) {

        validateWorkoutExists(workoutId);

        Pageable pageable = PageRequest.of(page, size);

        return workoutSectionRepository
                .findAllByWorkoutId(workoutId, pageable)
                .map(workoutSectionMapper::toDetailsDto);
    }

    public WorkoutSectionDetailsDto findById(Long workoutId,
                                             Long sectionId) {

        WorkoutSection section = workoutSectionRepository.findById(sectionId)
                .orElseThrow(() ->
                        GymJourneyException.notFound("Seção de treino não encontrada!")
                );

        return workoutSectionMapper.toDetailsDto(section);
    }

    public void delete(Long workoutId, Long sectionId) {

        WorkoutSection section = workoutSectionRepository.findById(sectionId)
                .orElseThrow(() ->
                        GymJourneyException.notFound("Seção de treino não encontrada!")
                );

        workoutSectionRepository.delete(section);
    }

    private void validateWorkoutExists(Long workoutId) {
        if (!workoutRepository.existsById(workoutId)) {
            throw GymJourneyException.notFound("Treino não encontrado!");
        }
    }
}
