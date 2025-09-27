package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.domain.Exercise;
import io.github.mendjoy.gymJourneyAPI.dto.ExerciseDto;
import io.github.mendjoy.gymJourneyAPI.repository.ExerciseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public ExerciseDto register(ExerciseDto exerciseDto){

        if(exerciseRepository.existsByName(exerciseDto.name())){
            throw new RuntimeException("Exercicio já cadastrado!");
        }

        Exercise newExercise = new Exercise(exerciseDto);

        return new ExerciseDto(exerciseRepository.save(newExercise));

    }

    public ExerciseDto getExerciseById(Integer id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Exercicio não encontrado!"));

        return new ExerciseDto(exercise);
    }

    public Page<ExerciseDto> getAllExercises(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercisePage = exerciseRepository.findAll(pageable);

        return exercisePage.map(ExerciseDto::new);
    }

    public Page<ExerciseDto> searchExercisesByName(String name, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercisePage = exerciseRepository.findByNameContainingIgnoreCase(name, pageable);
        return exercisePage.map(ExerciseDto::new);

    }

}
