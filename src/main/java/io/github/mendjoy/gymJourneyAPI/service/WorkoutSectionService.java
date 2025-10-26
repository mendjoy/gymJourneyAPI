package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.domain.Workout;
import io.github.mendjoy.gymJourneyAPI.domain.WorkoutSection;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDto;
import io.github.mendjoy.gymJourneyAPI.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutRepository;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutSectionRepository;
import io.github.mendjoy.gymJourneyAPI.utils.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutSectionService {

    private final WorkoutRepository workoutRepository;
    private final WorkoutSectionRepository workoutSectionRepository;
    private final ModelMapper modelMapper;

    public WorkoutSectionService(WorkoutRepository workoutRepository, WorkoutSectionRepository workoutSectionRepository, ModelMapper modelMapper) {
        this.workoutRepository = workoutRepository;
        this.workoutSectionRepository = workoutSectionRepository;
        this.modelMapper = modelMapper;
    }

    public List<WorkoutSectionDto> registerWorkoutSection(List<WorkoutSectionDto> workoutSectionDtos) {
        List<WorkoutSection> workoutSections = workoutSectionDtos.stream().map( section -> {
            Workout workout =
                    workoutRepository.findById(section.getWorkoutId()).orElseThrow(() -> GymJourneyException.notFound("Treino não " +
                    "encontrado!"));
            WorkoutSection workoutSection = modelMapper.map(section, WorkoutSection.class);
            workoutSection.setWorkout(workout);
            return workoutSection;
        }).toList();

        List<WorkoutSection> newSections = workoutSectionRepository.saveAll(workoutSections);

        return newSections.stream()
                .map(section -> modelMapper.map(section, WorkoutSectionDto.class))
                .toList();
    }

    public Page<WorkoutSectionDetailsDto> getSectionsByWorkoutId(Long workoutId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<WorkoutSection> workoutSections = workoutSectionRepository.findAllByWorkoutId(workoutId, pageable);

        return workoutSections.map(section -> {
            WorkoutSectionDetailsDto workoutSectionDetailsDto = modelMapper.map(section, WorkoutSectionDetailsDto.class);
            List<WorkoutExerciseDetailsDto> exercises = section.getWorkoutExercises().stream()
                    .map(ex -> modelMapper.map(ex, WorkoutExerciseDetailsDto.class))
                    .toList();
            workoutSectionDetailsDto.setWorkoutExercises(exercises);
            return workoutSectionDetailsDto;
        });
    }

    public void deleteWorkoutSection(Long id) {
        WorkoutSection workoutSection = workoutSectionRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound(
                "Seção de treino " +
                "não encontrada!"));
        workoutSectionRepository.delete(workoutSection);
    }

    public WorkoutSectionDto updateWorkoutSection(WorkoutSectionDto workoutSectionDto) {
        ValidationUtils.validateIdNotNull(workoutSectionDto.getId(), "Seção de treino");
        WorkoutSection section = workoutSectionRepository.findById(workoutSectionDto.getId()).orElseThrow(() -> GymJourneyException.notFound("Seção de treino não encontrada!"));
        section.update(workoutSectionDto);
        WorkoutSection updatedSection = workoutSectionRepository.save(section);
        return modelMapper.map(updatedSection, WorkoutSectionDto.class);
    }

    public WorkoutSectionDetailsDto getWorkoutSectionById(Long id) {
        WorkoutSection section = workoutSectionRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Seção " +
                "de treino não " +
                "encontrada!"));
        WorkoutSectionDetailsDto sectionDto = modelMapper.map(section, WorkoutSectionDetailsDto.class);
        List<WorkoutExerciseDetailsDto> exercises = section.getWorkoutExercises().stream()
                .map(ex -> modelMapper.map(ex, WorkoutExerciseDetailsDto.class))
                .toList();

        sectionDto.setWorkoutExercises(exercises);
        return sectionDto;
    }
}
