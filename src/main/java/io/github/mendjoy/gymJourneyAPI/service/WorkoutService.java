package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.domain.Workout;
import io.github.mendjoy.gymJourneyAPI.dto.WorkoutDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.WorkoutDto;
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

    public WorkoutDto register(WorkoutDto workoutDto){
        User user = userRepository.findById(workoutDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        Workout workout = modelMapper.map(workoutDto, Workout.class);
        workout.setUser(user);
        Workout newWorkout = workoutRepository.save(workout);

        return modelMapper.map(newWorkout, WorkoutDto.class);
    }

    public WorkoutDetailsDto getWorkoutById(Integer id){
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new RuntimeException("Treino não encontrado!"));
        return modelMapper.map(workout, WorkoutDetailsDto.class);
    }

    public Page<WorkoutDetailsDto> getWorkoutsByUser(Integer id, int page, int size) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        Pageable pageable = PageRequest.of(page, size);
        Page<Workout> workouts = workoutRepository.findAllByUser(user, pageable);
        return workouts.map(workout -> modelMapper.map(workout, WorkoutDetailsDto.class));
    }

    public void delete(Integer id) {
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new RuntimeException("Treino não encontrado!"));
        workoutRepository.deleteById(workout.getId());
    }

    public WorkoutDto update(WorkoutDto workoutDto) {
        Workout workout = workoutRepository.findById(workoutDto.getId()).orElseThrow(() -> new RuntimeException("Treino não encontrado!"));
        workout.update(workoutDto);
        Workout updatedWorkout = workoutRepository.save(workout);
        return modelMapper.map(updatedWorkout, WorkoutDto.class);
    }
}
