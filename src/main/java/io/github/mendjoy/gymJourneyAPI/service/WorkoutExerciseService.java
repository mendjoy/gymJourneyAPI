package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.config.mapper.WorkoutExerciseMapper;
import io.github.mendjoy.gymJourneyAPI.config.utils.ValidationUtils;
import io.github.mendjoy.gymJourneyAPI.domain.Exercise;
import io.github.mendjoy.gymJourneyAPI.domain.WorkoutExercise;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDto;
import io.github.mendjoy.gymJourneyAPI.repository.ExerciseRepository;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutExerciseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutExerciseMapper workoutExerciseMapper;

    public WorkoutExerciseService(WorkoutExerciseRepository workoutExerciseRepository, ExerciseRepository exerciseRepository, WorkoutExerciseMapper workoutExerciseMapper) {
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutExerciseMapper = workoutExerciseMapper;
    }

    public List<WorkoutExerciseDto> registerWorkoutExercise(List<WorkoutExerciseDto> workoutExerciseDtos) {
        List<WorkoutExercise> workoutExercises = workoutExerciseDtos.stream().map(dto -> {
            Exercise exercise = exerciseRepository.findById(dto.exerciseId())
                    .orElseThrow(() -> GymJourneyException.notFound("Exercício não encontrado!"));

            WorkoutExercise workoutExercise = workoutExerciseMapper.toEntity(dto);
            workoutExercise.setExercise(exercise);
            return workoutExercise;
        }).toList();

        List<WorkoutExercise> newWorkoutExercises = workoutExerciseRepository.saveAll(workoutExercises);
        return newWorkoutExercises.stream()
                .map(workoutExerciseMapper::toDto)
                .toList();
    }

    public Page<WorkoutExerciseDetailsDto> getExercisesBySectionId(Long workoutSectionId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<WorkoutExercise> workoutExercises = workoutExerciseRepository.findAllByWorkoutSectionId(workoutSectionId, pageable);

        return workoutExercises.map(workoutExerciseMapper::toDetailsDto);
    }

    public void deleteWorkoutExercise(Long id) {
        WorkoutExercise workoutExercise =
                workoutExerciseRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Exercicio " +
                "relacionado a treino não encontrado!"));
        workoutExerciseRepository.delete(workoutExercise);
    }

    public WorkoutExerciseDto updateWorkoutExercise(WorkoutExerciseDto workoutExerciseDto) {
        ValidationUtils.validateIdNotNull(workoutExerciseDto.id(), "Exercicio");
        WorkoutExercise workoutExercise =
                workoutExerciseRepository.findById(workoutExerciseDto.id()).orElseThrow(() -> GymJourneyException.notFound("Exercicio " +
                        "relacionado a treino não encontrado!"));
        workoutExercise.update(workoutExerciseDto);

        if(workoutExerciseDto.id() != null){
           Exercise exercise =
                   exerciseRepository.findById(workoutExerciseDto.id()).orElseThrow(() -> GymJourneyException.notFound(
                   "Exercicio não encontrado!"));
           workoutExercise.setExercise(exercise);
        }

        WorkoutExercise updatedWorkoutExercise = workoutExerciseRepository.save(workoutExercise);
        return workoutExerciseMapper.toDto(updatedWorkoutExercise);
    }
}
