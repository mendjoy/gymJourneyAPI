package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.domain.Workout;
import io.github.mendjoy.gymJourneyAPI.dto.WorkoutDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.WorkoutDto;
import io.github.mendjoy.gymJourneyAPI.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService {
    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final ModelMapper modelMapper;

    public WorkoutService(UserRepository userRepository, WorkoutRepository workoutRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.modelMapper = modelMapper;
    }

    public WorkoutDto registerWorkout(WorkoutDto workoutDto){
        validateWorkout(workoutDto, false);
        User user = userRepository.findById(workoutDto.getUserId())
                .orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado!"));
        Workout workout = modelMapper.map(workoutDto, Workout.class);
        workout.setUser(user);
        Workout newWorkout = workoutRepository.save(workout);
        return modelMapper.map(newWorkout, WorkoutDto.class);
    }

    public WorkoutDetailsDto getWorkoutById(Integer id){
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Treino não encontrado!"));
        return modelMapper.map(workout, WorkoutDetailsDto.class);
    }

    public Page<WorkoutDetailsDto> getWorkoutsByUser(Integer id, int page, int size) {
        User user = userRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado!"));
        Pageable pageable = PageRequest.of(page, size);
        Page<Workout> workouts = workoutRepository.findAllByUser(user, pageable);
        return workouts.map(workout -> modelMapper.map(workout, WorkoutDetailsDto.class));
    }

    public void deleteWorkout(Integer id) {
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Treino não encontrado!"));
        workoutRepository.deleteById(workout.getId());
    }

    public WorkoutDto updateWorkout(WorkoutDto workoutDto) {
        validateWorkout(workoutDto, true);
        Workout workout = workoutRepository.findById(workoutDto.getId()).orElseThrow(() -> GymJourneyException.notFound("Treino não encontrado!"));
        workout.updateWorkout(workoutDto);
        Workout updatedWorkout = workoutRepository.save(workout);
        return modelMapper.map(updatedWorkout, WorkoutDto.class);
    }

    private void validateWorkout(WorkoutDto workoutDto, boolean isUpdate){
        if (!isUpdate || (workoutDto.getName() != null)) {
            if (workoutDto.getName() == null || workoutDto.getName().isBlank()) {
                throw GymJourneyException.badRequest("O nome do treino deve ser informado!");
            }
        }
        if (!isUpdate || (workoutDto.getDescription() != null)) {
            if (workoutDto.getDescription() == null || workoutDto.getDescription().isBlank()) {
                throw GymJourneyException.badRequest("A descrição do treino deve ser informada!");
            }
        }
        if (!isUpdate || (workoutDto.getMaxSessions() != null)) {
            if (workoutDto.getMaxSessions() == null || workoutDto.getMaxSessions() <= 0) {
                throw GymJourneyException.badRequest("O número de sessões do treino deve ser informado e deve ser maior que zero!");
            }
        }
        if (!isUpdate || workoutDto.getStartDate() != null) {
            if (workoutDto.getStartDate() == null) {
                throw GymJourneyException.badRequest("A data de início do treino deve ser informada!");
            }
        }
    }
}
