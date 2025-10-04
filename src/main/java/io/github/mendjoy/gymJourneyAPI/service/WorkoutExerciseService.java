package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.domain.Exercise;
import io.github.mendjoy.gymJourneyAPI.domain.WorkoutExercise;
import io.github.mendjoy.gymJourneyAPI.dto.WorkoutExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.WorkoutExerciseDto;
import io.github.mendjoy.gymJourneyAPI.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.repository.ExerciseRepository;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutExerciseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final ModelMapper modelMapper;

    public WorkoutExerciseService(WorkoutExerciseRepository workoutExerciseRepository, ExerciseRepository exerciseRepository, ModelMapper modelMapper) {
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.exerciseRepository = exerciseRepository;
        this.modelMapper = modelMapper;
    }

    public List<WorkoutExerciseDto> registerWorkoutExercise(List<WorkoutExerciseDto> workoutExerciseDto) {
        List<WorkoutExercise> workoutExercises = workoutExerciseDto.stream().map( we -> {
            Exercise exercise = exerciseRepository.findById(we.getExerciseId()).orElseThrow(() -> GymJourneyException.notFound("Exercicio não encontrado!"));
            WorkoutExercise workoutExercise = modelMapper.map(we, WorkoutExercise.class);
            workoutExercise.setExercise(exercise);
            return workoutExercise;
        }).toList();

        List<WorkoutExercise> newWorkoutExercises = workoutExerciseRepository.saveAll(workoutExercises);

        return newWorkoutExercises.stream()
                .map(we -> modelMapper.map(we, WorkoutExerciseDto.class))
                .toList();
    }

    public Page<WorkoutExerciseDetailsDto> getExercisesBySectionId(Integer workoutSectionId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<WorkoutExercise> workoutExercises = workoutExerciseRepository.findAllByWorkoutSectionId(workoutSectionId, pageable);

        return workoutExercises.map( we -> {
            return modelMapper.map(we, WorkoutExerciseDetailsDto.class);
        });
    }

    public void deleteWorkoutExercise(Integer id) {
        WorkoutExercise workoutExercise =
                workoutExerciseRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Exercicio " +
                "relacionado a treino não encontrado!"));
        workoutExerciseRepository.deleteById(id);
    }

    public WorkoutExerciseDto updateWorkoutExercise(WorkoutExerciseDto workoutExerciseDto) {
        WorkoutExercise workoutExercise =
                workoutExerciseRepository.findById(workoutExerciseDto.getId()).orElseThrow(() -> GymJourneyException.notFound("Exercicio " +
                        "relacionado a treino não encontrado!"));
        workoutExercise.update(workoutExerciseDto);

        if(workoutExerciseDto.getExerciseId() != null){
           Exercise exercise =
                   exerciseRepository.findById(workoutExerciseDto.getExerciseId()).orElseThrow(() -> GymJourneyException.notFound(
                   "Exercicio não encontrado!"));
           workoutExercise.setExercise(exercise);
        }

        WorkoutExercise updatedWorkoutExercise = workoutExerciseRepository.save(workoutExercise);
        return modelMapper.map(updatedWorkoutExercise, WorkoutExerciseDto.class);
    }
}
