package io.github.mendjoy.gymJourneyAPI.service.exercise;

import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDTO;
import io.github.mendjoy.gymJourneyAPI.entity.exercise.Exercise;
import io.github.mendjoy.gymJourneyAPI.repository.ExerciseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public ExerciseDTO register(ExerciseDTO exerciseDTO){
        if(exerciseRepository.existsByName(exerciseDTO.getName())){
            throw new RuntimeException("Exercicio já cadastrado!");
        }

        Exercise newExercise = new Exercise(exerciseDTO.getName(),
                                            exerciseDTO.getDescription(),
                                            exerciseDTO.getMuscleGroup());

        Exercise savedExercise = exerciseRepository.save(newExercise);

        return new ExerciseDTO(savedExercise.getId(),
                               savedExercise.getName(),
                               savedExercise.getDescription(),
                               savedExercise.getMuscleGroup());
    }

    public ExerciseDTO getExerciseById(Integer id){
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Exercicio não encontrado!"));
        return new ExerciseDTO(exercise.getId(),
                               exercise.getName(),
                               exercise.getDescription(),
                               exercise.getMuscleGroup());
    }

    public Page<ExerciseDTO> getAllExercises(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercisePage = exerciseRepository.findAll(pageable);

        return exercisePage.map(exercise -> new ExerciseDTO(exercise.getId(),
                                                                    exercise.getName(),
                                                                    exercise.getDescription(),
                                                                    exercise.getMuscleGroup()));
    }

    public Page<ExerciseDTO> searchExercisesByName(String name, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercisePage = exerciseRepository.findByNameContainingIgnoreCase(name, pageable);
        return exercisePage.map(exercise -> new ExerciseDTO(exercise.getId(),
                                                                    exercise.getName(),
                                                                    exercise.getDescription(),
                                                                    exercise.getMuscleGroup()));

    }
}
