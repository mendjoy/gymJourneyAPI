package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.domain.Exercise;
import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDto;
import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.config.mapper.ExerciseMapper;
import io.github.mendjoy.gymJourneyAPI.repository.ExerciseRepository;
import io.github.mendjoy.gymJourneyAPI.config.utils.ValidationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper mapper;

    public ExerciseService(ExerciseRepository exerciseRepository, ExerciseMapper mapper) {
        this.exerciseRepository = exerciseRepository;
        this.mapper = mapper;
    }

    public ExerciseDto registerExercise(ExerciseDto exerciseDto){
        if(exerciseRepository.existsByName(exerciseDto.name())){
            throw GymJourneyException.alreadyExists("Exercicio " + exerciseDto.name() + " já cadastrado!");
        }
        Exercise exercise = mapper.toEntity(exerciseDto);
        Exercise newExercise = exerciseRepository.save(exercise);
        return mapper.toDto(newExercise);
    }

    public ExerciseDto getExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Exercicio não encontrado!"));
        return mapper.toDto(exercise);
    }

    public Page<ExerciseDto> getAllExercises(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercisePage = exerciseRepository.findAll(pageable);
        return exercisePage.map(mapper::toDto);
    }

    public Page<ExerciseDto> searchExercisesByName(String name, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercisePage = exerciseRepository.findByNameContainingIgnoreCase(name, pageable);
        return exercisePage.map(mapper::toDto);
    }

    public ExerciseDto updateExercise(ExerciseDto exerciseDto) {
        ValidationUtils.validateIdNotNull(exerciseDto.id(), "Exercicio");
        Exercise exercise = exerciseRepository.findById(exerciseDto.id()).orElseThrow(() -> GymJourneyException.notFound("Exercicio não encontrado!"));
        if(exerciseRepository.existsByName(exerciseDto.name())){
            throw GymJourneyException.alreadyExists("Exercicio " + exerciseDto.name() + " já cadastrado!");
        }
        exercise.updateExercise(exerciseDto);
        Exercise newExercise = exerciseRepository.save(exercise);
        return mapper.toDto(newExercise);
    }
}
