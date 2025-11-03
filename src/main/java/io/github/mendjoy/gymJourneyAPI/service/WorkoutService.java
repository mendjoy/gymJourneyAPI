package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.domain.Workout;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDto;
import io.github.mendjoy.gymJourneyAPI.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.mapper.WorkoutMapper;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutRepository;
import io.github.mendjoy.gymJourneyAPI.utils.ValidationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService {
    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutMapper workoutMapper;

    public WorkoutService(UserRepository userRepository, WorkoutRepository workoutRepository, WorkoutMapper workoutMapper) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.workoutMapper = workoutMapper;
    }

    public WorkoutDto registerWorkout(WorkoutDto workoutDto){
        User user = userRepository.findById(workoutDto.userId())
                .orElseThrow(() -> GymJourneyException.notFound("Usuário " + workoutDto.userId() + " não encontrado!"));
        Workout workout = workoutMapper.toEntity(workoutDto);
        workout.setUser(user);
        Workout newWorkout = workoutRepository.save(workout);
        return workoutMapper.toDto(newWorkout);
    }

    public WorkoutDetailsDto getWorkoutById(Long id){
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Treino não encontrado!"));
        return workoutMapper.toDetailsDto(workout);
    }

    public Page<WorkoutDetailsDto> getWorkoutsByUser(Long id, int page, int size) {
        User user = userRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado!"));
        Pageable pageable = PageRequest.of(page, size);
        Page<Workout> workouts = workoutRepository.findAllByUser(user, pageable);
        return workouts.map(workoutMapper::toDetailsDto);
    }

    public void deleteWorkout(Long id) {
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Treino não encontrado!"));
        workoutRepository.delete(workout);
    }

    public WorkoutDto updateWorkout(WorkoutDto workoutDto) {
        ValidationUtils.validateIdNotNull(workoutDto.id(), "Treino");
        Workout workout = workoutRepository.findById(workoutDto.id()).orElseThrow(() -> GymJourneyException.notFound("Treino " + workoutDto.id() + " não encontrado!"));
        workout.updateWorkout(workoutDto);
        Workout updatedWorkout = workoutRepository.save(workout);
        return workoutMapper.toDto(updatedWorkout);
    }
}
