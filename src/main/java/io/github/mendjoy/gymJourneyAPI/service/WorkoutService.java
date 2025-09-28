package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.domain.*;
import io.github.mendjoy.gymJourneyAPI.dto.WorkoutDto;
import io.github.mendjoy.gymJourneyAPI.repository.ExerciseRepository;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutService {
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ModelMapper modelMapper;

    public WorkoutService(UserRepository userRepository, ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
        this.modelMapper = modelMapper;
    }

    public WorkoutDto register(WorkoutDto workoutDto){

        User user = userRepository.findById(workoutDto.getUserId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        LocalDate newStartDate = workoutDto.getStartDate();

        Workout workout = modelMapper.map(workoutDto, Workout.class);
        workout.setUser(user);

        List<WorkoutSection> workoutSections = workoutDto.getWorkoutSections().stream().map(sec -> {

            WorkoutSection section =  modelMapper.map(sec, WorkoutSection.class);
            section.setWorkout(workout);

            List<WorkoutExercise> workoutExercises = sec.getWorkoutExercises().stream().map(ex -> {
                Exercise exercise = exerciseRepository.findById(ex.getExerciseId()).orElseThrow(() -> new EntityNotFoundException("Exercício: " + ex.getExerciseId() + " não encontrado!"));

                 WorkoutExercise workoutExercise = modelMapper.map(ex, WorkoutExercise.class);
                 workoutExercise.setExercise(exercise);
                 workoutExercise.setWorkoutSection(section);
                 return workoutExercise;
            }).toList();

            section.setWorkoutExercises(workoutExercises);
            return section;
        }).toList();

        workout.setWorkoutSections(workoutSections);

        Workout newWorkout = workoutRepository.save(workout);
        return modelMapper.map(workout, WorkoutDto.class);
    }
}
