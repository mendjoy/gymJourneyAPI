package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDTO;
import io.github.mendjoy.gymJourneyAPI.dto.workout.*;
import io.github.mendjoy.gymJourneyAPI.dto.workout.request.WorkoutRequestDTO;
import io.github.mendjoy.gymJourneyAPI.dto.workout.response.WorkoutExerciseResponseDTO;
import io.github.mendjoy.gymJourneyAPI.dto.workout.response.WorkoutResponseDTO;
import io.github.mendjoy.gymJourneyAPI.dto.workout.response.WorkoutSectionResponseDTO;
import io.github.mendjoy.gymJourneyAPI.entity.exercise.Exercise;
import io.github.mendjoy.gymJourneyAPI.entity.user.User;
import io.github.mendjoy.gymJourneyAPI.entity.workout.Workout;
import io.github.mendjoy.gymJourneyAPI.entity.workout.WorkoutExercise;
import io.github.mendjoy.gymJourneyAPI.entity.workout.WorkoutSection;
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

    public WorkoutDTO register(WorkoutRequestDTO workoutRequestDTO){

        User user = userRepository.findById(workoutRequestDTO.getUserId()).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        Workout workout = new Workout(user, workoutRequestDTO.getName(), workoutRequestDTO.getDescription(), workoutRequestDTO.getMaxSessions());

        List<WorkoutSection> workoutSections = workoutRequestDTO.getWorkoutSection().stream().map(sec -> {

            WorkoutSection section = new WorkoutSection(workout, sec.getName(), sec.getDescription());

            List<WorkoutExercise> workoutExercises = sec.getWorkoutExercise().stream().map(ex -> {
                Exercise exercise = exerciseRepository.findById(ex.getId()).orElseThrow(() -> new EntityNotFoundException("Exercício: " + ex.getId() + " não encontrado!"));
                return new WorkoutExercise(section, exercise, ex.getSets(), ex.getRepetitions(), ex.getWeight(), ex.getRestTime());
            }).toList();

            section.setWorkoutExercises(workoutExercises);
            return section;
        }).toList();

         workout.setWorkoutSections(workoutSections);

         Workout newWorkout = workoutRepository.save(workout);

         return getWorkoutDetailsDTO(newWorkout);
    }

    private WorkoutResponseDTO getWorkoutDetailsDTO(Workout workout){

        List<WorkoutSectionResponseDTO> workoutSectionResponseDTO = workout.getWorkoutSections().stream().map( ws -> {

            List<WorkoutExerciseResponseDTO> workoutExerciseResponseDTOS = ws.getWorkoutExercises().stream().map(we -> {
                Exercise exercise = exerciseRepository.findById(we.getExercise().getId()).orElseThrow(() -> new EntityNotFoundException("Exercício não encontrado!"));
                ExerciseDTO exerciseDTO = new ExerciseDTO(exercise.getId(),
                                                          exercise.getName(),
                                                          exercise.getDescription(),
                                                          exercise.getMuscle_group());

                return new WorkoutExerciseResponseDTO(we.getId(),
                                                      we.getSets(),
                                                      we.getRepetitions(),
                                                      we.getWeight(),
                                                      we.getRestTime(),exerciseDTO);
            }).toList();

            return new WorkoutSectionResponseDTO(ws.getId(), ws.getName(), ws.getDescription(), workoutExerciseResponseDTOS);
        }).toList();

        return new WorkoutResponseDTO(workout.getId(),
                                      workout.getUser().getId(),
                                      workout.getName(),
                                      workout.getDescription(),
                                      workout.getMaxSessions(),
                                      workoutSectionResponseDTO);

    }
}
