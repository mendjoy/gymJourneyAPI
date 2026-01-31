package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.config.mapper.WorkoutSectionMapper;
import io.github.mendjoy.gymJourneyAPI.config.utils.ValidationUtils;
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

    public WorkoutSectionService(WorkoutRepository workoutRepository, WorkoutSectionRepository workoutSectionRepository, WorkoutSectionMapper workoutSectionMapper) {
        this.workoutRepository = workoutRepository;
        this.workoutSectionRepository = workoutSectionRepository;
        this.workoutSectionMapper = workoutSectionMapper;
    }

    public List<WorkoutSectionDto> create(List<WorkoutSectionDto> workoutSectionDtos) {
        List<WorkoutSection> workoutSections = workoutSectionDtos.stream().map(sectionDto -> {
            Workout workout =
                    workoutRepository.findById(sectionDto.workoutId()).orElseThrow(() -> GymJourneyException.notFound("Treino não " +
                            "encontrado!"));
            WorkoutSection section = workoutSectionMapper.toEntity(sectionDto);
            section.setWorkout(workout);
            return section;
        }).toList();

        List<WorkoutSection> newSections = workoutSectionRepository.saveAll(workoutSections);

        return newSections.stream()
                .map(workoutSectionMapper::toDto)
                .toList();
    }

    public WorkoutSectionDto update(WorkoutSectionDto workoutSectionDto) {
        WorkoutSection section = workoutSectionRepository.findById(workoutSectionDto.id()).orElseThrow(() -> GymJourneyException.notFound("Seção de treino não encontrada!"));
        section.update(workoutSectionDto);
        WorkoutSection updatedSection = workoutSectionRepository.save(section);
        return workoutSectionMapper.toDto(updatedSection);
    }

    public Page<WorkoutSectionDetailsDto> findByWorkoutId(Long workoutId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WorkoutSection> workoutSections = workoutSectionRepository.findAllByWorkoutId(workoutId, pageable);
        return workoutSections.map(workoutSectionMapper::toDetailsDto);
    }

    public void delete(Long id) {
        WorkoutSection workoutSection = workoutSectionRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound(
                "Seção de treino " +
                        "não encontrada!"));
        workoutSectionRepository.delete(workoutSection);
    }

    public WorkoutSectionDetailsDto findById(Long id) {
        WorkoutSection section = workoutSectionRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Seção " +
                "de treino não " +
                "encontrada!"));
        return workoutSectionMapper.toDetailsDto(section);
    }
}
