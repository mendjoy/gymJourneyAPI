package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDTO;
import io.github.mendjoy.gymJourneyAPI.entity.exercise.Exercise;
import io.github.mendjoy.gymJourneyAPI.entity.user.User;
import io.github.mendjoy.gymJourneyAPI.entity.workout.Workout;
import io.github.mendjoy.gymJourneyAPI.entity.workout.WorkoutExercise;
import io.github.mendjoy.gymJourneyAPI.repository.ExerciseRepository;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    public WorkoutDTO register(WorkoutDTO workoutDTO){

        User user = userRepository.findById(workoutDTO.getUserId()).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        Workout workout = new Workout(user, workoutDTO.getName(), workoutDTO.getDescription());

        List<WorkoutExercise> workoutExercises = workoutDTO.getExercises().stream().map(ex -> {
            Exercise exercise = exerciseRepository.findById(ex.getExerciseId())
                                                  .orElseThrow(() -> new EntityNotFoundException("Exercício: " + ex.getExerciseId() + " não encontrado!"));
            return new WorkoutExercise(workout, exercise, ex.getSets(), ex.getRepetitions(),
                    ex.getWeight(), ex.getRestTime());
        }).toList();

        workout.setWorkoutExercises(workoutExercises);

         Workout newWorkout = workoutRepository.save(workout);
         workoutDTO.setId(newWorkout.getId());
         return workoutDTO;

    }
}
