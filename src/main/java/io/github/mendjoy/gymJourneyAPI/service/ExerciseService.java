package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.config.mapper.ExerciseMapper;
import io.github.mendjoy.gymJourneyAPI.domain.Exercise;
import io.github.mendjoy.gymJourneyAPI.domain.MuscleGroup;
import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDto;
import io.github.mendjoy.gymJourneyAPI.dto.muscleGroup.MuscleGroupDto;
import io.github.mendjoy.gymJourneyAPI.repository.ExerciseRepository;
import io.github.mendjoy.gymJourneyAPI.repository.MuscleGroupRepository;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutExerciseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final ExerciseMapper exerciseMapper;
    private final WorkoutExerciseRepository workoutExerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository,
                           MuscleGroupRepository muscleGroupRepository,
                           ExerciseMapper exerciseMapper, WorkoutExerciseRepository workoutExerciseRepository) {
        this.exerciseRepository = exerciseRepository;
        this.muscleGroupRepository = muscleGroupRepository;
        this.exerciseMapper = exerciseMapper;
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    @Transactional
    public ExerciseDetailsDto register(ExerciseDto exerciseDto) {
        if (exerciseRepository.existsByName(exerciseDto.name())) {
            throw GymJourneyException.alreadyExists(
                    "Exercício '" + exerciseDto.name() + "' já cadastrado!"
            );
        }

        if (exerciseDto.muscleGroupIds() == null || exerciseDto.muscleGroupIds().isEmpty()) {
            throw GymJourneyException.badRequest(
                    "É necessário informar pelo menos um grupo muscular"
            );
        }

        Set<MuscleGroup> groups = exerciseDto.muscleGroupIds().stream()
                .map(id -> muscleGroupRepository.findById(id)
                        .orElseThrow(() -> GymJourneyException.notFound(
                                "Grupo muscular não encontrado: " + id
                        )))
                .collect(Collectors.toSet());

        Exercise exercise = exerciseMapper.toEntity(exerciseDto);
        exercise.setMuscleGroups(groups);

        Exercise newExercise = exerciseRepository.save(exercise);
        return exerciseMapper.toDetailsDto(newExercise);
    }

    @Transactional
    public ExerciseDetailsDto update(Long id, ExerciseDto exerciseDto) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> GymJourneyException.notFound("Exercício não encontrado!"));

        if (exerciseDto.name() != null &&
                !exerciseDto.name().equals(exercise.getName()) &&
                exerciseRepository.existsByName(exerciseDto.name())) {
            throw GymJourneyException.alreadyExists(
                    "Exercício '" + exerciseDto.name() + "' já cadastrado!"
            );
        }

        exercise.update(exerciseDto);

        if (exerciseDto.muscleGroupIds() != null && !exerciseDto.muscleGroupIds().isEmpty()) {
            Set<MuscleGroup> groups = exerciseDto.muscleGroupIds().stream()
                    .map(muscleGroupId -> muscleGroupRepository.findById(muscleGroupId)
                            .orElseThrow(() -> GymJourneyException.notFound(
                                    "Grupo muscular não encontrado: " + muscleGroupId
                            )))
                    .collect(Collectors.toSet());
            exercise.setMuscleGroups(groups);
        }

        Exercise updatedExercise = exerciseRepository.save(exercise);
        return exerciseMapper.toDetailsDto(updatedExercise);
    }

    public ExerciseDetailsDto getById(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> GymJourneyException.notFound("Exercício não encontrado!"));
        return exerciseMapper.toDetailsDto(exercise);
    }

    public Page<ExerciseDetailsDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercisePage = exerciseRepository.findAll(pageable);
        return exercisePage.map(exerciseMapper::toDetailsDto);
    }

    public Page<ExerciseDetailsDto> searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercisePage = exerciseRepository.findByNameContainingIgnoreCase(name, pageable);
        return exercisePage.map(exerciseMapper::toDetailsDto);
    }

    @Transactional
    public void addMuscleGroup(Long exerciseId, MuscleGroupDto muscleGroupDto) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> GymJourneyException.notFound("Exercício não encontrado!"));

        MuscleGroup muscleGroup = muscleGroupRepository.findById(muscleGroupDto.id())
                .orElseThrow(() -> GymJourneyException.notFound("Grupo muscular não encontrado!"));

        if (exercise.getMuscleGroups().contains(muscleGroup)) {
            throw GymJourneyException.conflict(
                    "O exercício já possui este grupo muscular"
            );
        }

        exercise.getMuscleGroups().add(muscleGroup);
        exerciseRepository.save(exercise);
    }

    @Transactional
    public void removeMuscleGroup(Long exerciseId, MuscleGroupDto muscleGroupDto) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> GymJourneyException.notFound("Exercício não encontrado!"));

        MuscleGroup muscleGroup = muscleGroupRepository.findById(muscleGroupDto.id())
                .orElseThrow(() -> GymJourneyException.notFound("Grupo muscular não encontrado!"));

        if (!exercise.getMuscleGroups().contains(muscleGroup)) {
            throw GymJourneyException.badRequest(
                    "O exercício não possui este grupo muscular"
            );
        }

        if (exercise.getMuscleGroups().size() == 1) {
            throw GymJourneyException.badRequest(
                    "Não é possível remover o último grupo muscular do exercício"
            );
        }

        exercise.getMuscleGroups().remove(muscleGroup);
        exerciseRepository.save(exercise);
    }

    @Transactional
    public void delete(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> GymJourneyException.notFound("Exercício não encontrado!"));

        if (workoutExerciseRepository.existsByExerciseId(id)) {
            throw GymJourneyException.conflict(
                    "Não é possível deletar este exercício pois ele está sendo usado em treinos"
         );
        }

        exerciseRepository.delete(exercise);
    }
}