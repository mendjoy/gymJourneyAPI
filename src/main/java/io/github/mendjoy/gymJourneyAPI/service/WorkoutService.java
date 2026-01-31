package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.config.mapper.WorkoutMapper;
import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.domain.Workout;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDto;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class WorkoutService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final UserService userService;
    private final WorkoutMapper workoutMapper;

    public WorkoutService(UserRepository userRepository,
                          WorkoutRepository workoutRepository,
                          UserService userService,
                          WorkoutMapper workoutMapper) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.userService = userService;
        this.workoutMapper = workoutMapper;
    }

    @Transactional
    public WorkoutDetailsDto create(WorkoutDto workoutDto) {

        User user = userRepository.findById(workoutDto.userId())
                .orElseThrow(() -> GymJourneyException.notFound(
                        "Usuário não encontrado!"
                ));
        validateWorkoutDates(workoutDto.startDate(), workoutDto.endDate());
        Workout workout = workoutMapper.toEntity(workoutDto);
        workout.setUser(user);
        Workout newWorkout = workoutRepository.save(workout);
        return workoutMapper.toDetailsDto(newWorkout);
    }

    @Transactional
    public WorkoutDetailsDto update(Long id, WorkoutDto workoutDto) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> GymJourneyException.notFound(
                        "Treino não encontrado!"
                ));

        if (workoutDto.userId() != null &&
                !workoutDto.userId().equals(workout.getUser().getId())) {
            throw GymJourneyException.badRequest(
                    "Não é permitido transferir treinos entre usuários"
            );
        }

        LocalDate newStartDate = workoutDto.startDate() != null ?
                workoutDto.startDate() : workout.getStartDate();
        LocalDate newEndDate = workoutDto.endDate() != null ?
                workoutDto.endDate() : workout.getEndDate();

        validateWorkoutDates(newStartDate, newEndDate);

        workout.updateWorkout(workoutDto);
        Workout updatedWorkout = workoutRepository.save(workout);
        return workoutMapper.toDetailsDto(updatedWorkout);
    }

    public WorkoutDetailsDto findById(Long workoutId, User authenticatedUser) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> GymJourneyException.notFound("Treino não encontrado!"));

        if (userService.cannotAccess(workout.getUser(), authenticatedUser)) {
            throw GymJourneyException.forbidden("Você não tem permissão para realizar essa busca.");
        };

        return workoutMapper.toDetailsDto(workout);
    }

    public Page<WorkoutDetailsDto> findWorkouts(Long userId, Boolean active, int page, int size, User authenticatedUser) {

        Pageable pageable = PageRequest.of(page, size);
        Long userIdParam = userId == null ? authenticatedUser.getId() : userId;

        User user = userRepository.findById(userIdParam)
                .orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado!"));

        if (userService.cannotAccess(user, authenticatedUser)) {
            throw GymJourneyException.forbidden("Você não tem permissão para realizar essa busca.");
        }

        Page<Workout> workouts;

        if (active == null) {
            workouts = workoutRepository.findAllByUser(user, pageable);
        } else if (active) {
            workouts = workoutRepository.findActiveByUser(
                    user.getId(),
                    LocalDate.now(),
                    pageable
            );
        } else {
            workouts = workoutRepository.findInactiveByUser(
                    user.getId(),
                    LocalDate.now(),
                    pageable
            );
        }

        return workouts.map(workoutMapper::toDetailsDto);
    }

    @Transactional
    public void delete(Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> GymJourneyException.notFound("Treino não encontrado!"));
        workoutRepository.delete(workout);
    }

    private void validateWorkoutDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            throw GymJourneyException.badRequest("A data de início é obrigatória");
        }

        if (endDate != null && endDate.isBefore(startDate)) {
            throw GymJourneyException.badRequest(
                    "A data de término não pode ser anterior à data de início"
            );
        }
    }

}