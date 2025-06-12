package io.github.mendjoy.gymJourneyAPI.service.workout;

import io.github.mendjoy.gymJourneyAPI.dto.workout.response.WorkoutFrequencyResponseDTO;
import io.github.mendjoy.gymJourneyAPI.entity.user.User;
import io.github.mendjoy.gymJourneyAPI.entity.workout.Workout;
import io.github.mendjoy.gymJourneyAPI.entity.workout.WorkoutFrequency;
import io.github.mendjoy.gymJourneyAPI.exception.custom.CustomGymJourneyApiException;
import io.github.mendjoy.gymJourneyAPI.repository.workout.WorkoutFrequencyRepository;
import io.github.mendjoy.gymJourneyAPI.repository.workout.WorkoutRepository;
import io.github.mendjoy.gymJourneyAPI.service.user.UserAuthService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class WorkoutFrequencyService {

    private final UserAuthService userAuthService;
    private final WorkoutRepository workoutRepository;
    private final WorkoutFrequencyRepository workoutFrequencyRepository;

    public WorkoutFrequencyService(UserAuthService userAuthService, WorkoutRepository workoutRepository, WorkoutFrequencyRepository workoutFrequencyRepository) {
        this.userAuthService = userAuthService;
        this.workoutRepository = workoutRepository;
        this.workoutFrequencyRepository = workoutFrequencyRepository;
    }

    public WorkoutFrequencyResponseDTO registerFrequency(Integer idWorkout, LocalDate date){
        User user = userAuthService.getUserAuthenticate();
        Workout currentWorkout = workoutRepository.findCurrentWorkout(user).orElseThrow(() -> new EntityNotFoundException("Não existe treino atual cadastrado para o usuario!"));
        Workout workout = workoutRepository.findById(idWorkout).orElseThrow(() -> new EntityNotFoundException("Treino não cadastrado!"));

        if(!Objects.equals(workout.getId(), currentWorkout.getId())){
            throw new CustomGymJourneyApiException(HttpStatus.FORBIDDEN, "Frequencia só pode ser registrada para o treino atual!");
        }

        if (date == null) {
            date = LocalDate.now();
        }
        if (date.isAfter(LocalDate.now())) {
            throw new CustomGymJourneyApiException(HttpStatus.BAD_REQUEST, "Não é permitido registrar frequência futura!");
        }

        if(workoutFrequencyRepository.existsByUserAndWorkoutAndFrequencyDate(user,workout,date)){
            throw new CustomGymJourneyApiException(HttpStatus.CONFLICT,  String.format("Frequência já registrada para a data %s.", date));
        }

        WorkoutFrequency workoutFrequency = new WorkoutFrequency(user, workout, date);
        WorkoutFrequency newWorkoutFrequency = workoutFrequencyRepository.save(workoutFrequency);
        return new WorkoutFrequencyResponseDTO(newWorkoutFrequency.getId(),
                                               newWorkoutFrequency.getUser().getId(),
                                               newWorkoutFrequency.getWorkout().getId(),
                                               newWorkoutFrequency.getFrequencyDate());
    }
}
