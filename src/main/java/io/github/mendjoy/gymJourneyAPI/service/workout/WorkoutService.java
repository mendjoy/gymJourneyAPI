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
import io.github.mendjoy.gymJourneyAPI.exception.custom.CustomGymJourneyApiException;
import io.github.mendjoy.gymJourneyAPI.repository.ExerciseRepository;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class WorkoutService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserAuthService userAuthService;

    public WorkoutDTO register(WorkoutRequestDTO workoutRequestDTO){

        User user = userRepository.findById(workoutRequestDTO.getUserId()).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        LocalDate newStartDate = workoutRequestDTO.getStartDate();

        boolean hasConflict = workoutRepository.existsWorkoutInProgressForUser(user, newStartDate);

        if (hasConflict) {
            throw new CustomGymJourneyApiException(HttpStatus.CONFLICT, "O usuário já possui um treino ativo. Por favor, finalize o treino atual para prosseguir com o cadastro.");
        }

        Workout workout = new Workout(user,
                                      workoutRequestDTO.getName(),
                                      workoutRequestDTO.getDescription(),
                                      workoutRequestDTO.getMaxSessions(),
                                      workoutRequestDTO.getStartDate());

        List<WorkoutSection> workoutSections = workoutRequestDTO.getWorkoutSections().stream().map(sec -> {

            WorkoutSection section = new WorkoutSection(workout, sec.getName(), sec.getDescription());

            List<WorkoutExercise> workoutExercises = sec.getWorkoutExercises().stream().map(ex -> {
                Exercise exercise = exerciseRepository.findById(ex.getExerciseId()).orElseThrow(() -> new EntityNotFoundException("Exercício: " + ex.getExerciseId() + " não encontrado!"));
                return new WorkoutExercise(section,
                                           exercise,
                                           ex.getSets(),
                                           ex.getRepetitions(),
                                           ex.getWeight(),
                                           ex.getRestTime());
            }).toList();

            section.setWorkoutExercises(workoutExercises);
            return section;
        }).toList();

         workout.setWorkoutSections(workoutSections);

         Workout newWorkout = workoutRepository.save(workout);

         return getWorkoutDetailsDTO(newWorkout);
    }

    public void deleteWorkout(Integer id){
       Workout workout = workoutRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Treino não encontrado!"));
       if(workout != null){
           workoutRepository.delete(workout);
       }
    }

    public void finishWorkout(Integer id, LocalDate endDate){
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Treino não encontrado!"));
        if(workout != null){

            if (workout.getEndDate() != null) {
                throw new IllegalStateException("Treino já finalizado!");
            }else{
                workout.setEndDate(endDate);
                workoutRepository.save(workout);
            }
        }
    }

    public List<WorkoutResponseDTO> getAllWorkoutsByUser(Integer userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
        List<Workout> workouts = workoutRepository.findByUser(user);
        return workouts.stream()
                .map((this::getWorkoutDetailsDTO))
                .toList();

    }

    //Retorna o Treino do usuario autenticado
    public List<WorkoutResponseDTO> getAllWorkoutsByUser(){
        User user = userAuthService.getUserAuthenticate();
        List<Workout> workouts = workoutRepository.findByUser(user);
        return workouts.stream()
                .map((this::getWorkoutDetailsDTO))
                .toList();

    }

    public WorkoutResponseDTO getCurrentWorkoutByUser(Integer userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
        Workout workout = workoutRepository.findCurrentWorkout(user);
        return getWorkoutDetailsDTO(workout);
    }

    //Retorna o Treino do usuario autenticado
    public WorkoutResponseDTO getCurrentWorkoutByUser(){
        User user = userAuthService.getUserAuthenticate();
        Workout workout = workoutRepository.findCurrentWorkout(user);
        return getWorkoutDetailsDTO(workout);
    }

    private WorkoutResponseDTO getWorkoutDetailsDTO(Workout workout){

        List<WorkoutSectionResponseDTO> workoutSectionResponseDTO = workout.getWorkoutSections().stream().map( ws -> {

            List<WorkoutExerciseResponseDTO> workoutExerciseResponseDTOS = ws.getWorkoutExercises().stream().map(we -> {
                Exercise exercise = exerciseRepository.findById(we.getExercise().getId()).orElseThrow(() -> new EntityNotFoundException("Exercício não encontrado!"));
                ExerciseDTO exerciseDTO = new ExerciseDTO(exercise.getId(),
                                                          exercise.getName(),
                                                          exercise.getDescription(),
                                                          exercise.getMuscleGroup());

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
                                      workout.getStartDate(),
                                      workout.getEndDate(),
                                      workoutSectionResponseDTO);

    }
}
