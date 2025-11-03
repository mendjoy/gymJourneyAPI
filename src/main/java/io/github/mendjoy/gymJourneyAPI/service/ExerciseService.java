package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.config.mapper.ExerciseMapper;
import io.github.mendjoy.gymJourneyAPI.config.utils.ValidationUtils;
import io.github.mendjoy.gymJourneyAPI.domain.Exercise;
import io.github.mendjoy.gymJourneyAPI.domain.MuscleGroup;
import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDto;
import io.github.mendjoy.gymJourneyAPI.repository.ExerciseRepository;
import io.github.mendjoy.gymJourneyAPI.repository.MuscleGroupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final ExerciseMapper exerciseMappermapper;

    public ExerciseService(ExerciseRepository exerciseRepository, MuscleGroupRepository muscleGroupRepository, ExerciseMapper exerciseMappermapper) {
        this.exerciseRepository = exerciseRepository;
        this.muscleGroupRepository = muscleGroupRepository;
        this.exerciseMappermapper = exerciseMappermapper;
    }

    public ExerciseDetailsDto register(ExerciseDto exerciseDto){
        if(exerciseRepository.existsByName(exerciseDto.name())){
            throw GymJourneyException.alreadyExists("Exercicio " + exerciseDto.name() + " já cadastrado!");
        }

        Set<MuscleGroup> groups = exerciseDto.muscleGroupIds().stream()
                .map(id -> muscleGroupRepository.findById(id)
                        .orElseThrow(() -> GymJourneyException.notFound("Grupo muscular não encontrado: " + id)))
                .collect(Collectors.toSet());
        Exercise exercise = exerciseMappermapper.toEntity(exerciseDto);
        exercise.setMuscleGroups(groups);

        Exercise newExercise = exerciseRepository.save(exercise);
        return exerciseMappermapper.toDetailsDto(newExercise);
    }

    public ExerciseDto update(ExerciseDto exerciseDto) {
        ValidationUtils.validateIdNotNull(exerciseDto.id(), "Exercicio");
        Exercise exercise = exerciseRepository.findById(exerciseDto.id()).orElseThrow(() -> GymJourneyException.notFound("Exercicio não encontrado!"));
        if(exerciseRepository.existsByName(exerciseDto.name())){
            throw GymJourneyException.alreadyExists("Exercicio " + exerciseDto.name() + " já cadastrado!");
        }
        exercise.update(exerciseDto);

        Exercise newExercise = exerciseRepository.save(exercise);
        return exerciseMappermapper.toDto(newExercise);
    }

    public ExerciseDetailsDto getById(Long id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Exercicio não encontrado!"));
        return exerciseMappermapper.toDetailsDto(exercise);
    }

    public Page<ExerciseDetailsDto> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercisePage = exerciseRepository.findAll(pageable);
        return exercisePage.map(exerciseMappermapper::toDetailsDto);
    }

    public Page<ExerciseDetailsDto> searchByName(String name, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercisePage = exerciseRepository.findByNameContainingIgnoreCase(name, pageable);
        return exercisePage.map(exerciseMappermapper::toDetailsDto);
    }
}
