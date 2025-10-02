package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.domain.Exercise;
import io.github.mendjoy.gymJourneyAPI.dto.ExerciseDto;
import io.github.mendjoy.gymJourneyAPI.repository.ExerciseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ModelMapper modelMapper;

    public ExerciseService(ExerciseRepository exerciseRepository, ModelMapper modelMapper) {
        this.exerciseRepository = exerciseRepository;
        this.modelMapper = modelMapper;
    }

    public ExerciseDto register(ExerciseDto exerciseDto){

        if(exerciseRepository.existsByName(exerciseDto.getName())){
            throw new RuntimeException("Exercicio já cadastrado!");
        }

        Exercise newExercise = exerciseRepository.save(modelMapper.map(exerciseDto, Exercise.class));

        return modelMapper.map(newExercise, ExerciseDto.class);

    }

    public ExerciseDto getExerciseById(Integer id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Exercicio não encontrado!"));

        return modelMapper.map(exercise, ExerciseDto.class);
    }

    public Page<ExerciseDto> getAllExercises(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercisePage = exerciseRepository.findAll(pageable);
        return exercisePage.map(exercise -> modelMapper.map(exercise, ExerciseDto.class));
    }

    public Page<ExerciseDto> searchExercisesByName(String name, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercises = exerciseRepository.findByNameContainingIgnoreCase(name, pageable);
        return exercises.map(exercise -> modelMapper.map(exercise, ExerciseDto.class));
    }

    public ExerciseDto update(ExerciseDto exerciseDto) {
        Exercise exercise = exerciseRepository.findById(exerciseDto.getId()).orElseThrow(() -> new EntityNotFoundException("Exercicio não encontrado!"));
        exercise.update(exerciseDto);
        Exercise newExercise = exerciseRepository.save(exercise);
        return modelMapper.map(newExercise, ExerciseDto.class);
    }
}
