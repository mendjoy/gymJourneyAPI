package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.config.mapper.WorkoutExerciseMapper;
import io.github.mendjoy.gymJourneyAPI.domain.Exercise;
import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.domain.WorkoutExercise;
import io.github.mendjoy.gymJourneyAPI.domain.WorkoutSection;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDto;
import io.github.mendjoy.gymJourneyAPI.repository.ExerciseRepository;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutExerciseRepository;
import io.github.mendjoy.gymJourneyAPI.repository.WorkoutSectionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutSectionRepository workoutSectionRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutExerciseMapper workoutExerciseMapper;

    public WorkoutExerciseService(WorkoutExerciseRepository workoutExerciseRepository,
                                  WorkoutSectionRepository workoutSectionRepository,
                                  ExerciseRepository exerciseRepository,
                                  WorkoutExerciseMapper workoutExerciseMapper) {
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.workoutSectionRepository = workoutSectionRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutExerciseMapper = workoutExerciseMapper;
    }

    @Transactional
    public List<WorkoutExerciseDetailsDto> create(
            Long sectionId,
            List<WorkoutExerciseDto> workoutExerciseDtos) {

        WorkoutSection section = getSection(sectionId);

        if (workoutExerciseDtos == null || workoutExerciseDtos.isEmpty()) {
            throw GymJourneyException.badRequest(
                    "É necessário informar pelo menos um exercício"
            );
        }

        List<WorkoutExercise> exercises = workoutExerciseDtos.stream().map(dto -> {
            Exercise exercise = exerciseRepository.findById(dto.exerciseId())
                    .orElseThrow(() -> GymJourneyException.notFound(
                            "Exercício " + dto.exerciseId() + " não encontrado!"
                    ));

            WorkoutExercise workoutExercise = workoutExerciseMapper.toEntity(dto);
            workoutExercise.setWorkoutSection(section);
            workoutExercise.setExercise(exercise);
            return workoutExercise;
        }).toList();

        return workoutExerciseRepository.saveAll(exercises)
                .stream()
                .map(workoutExerciseMapper::toDetailsDto)
                .toList();
    }

    @Transactional
    public WorkoutExerciseDetailsDto update(
            Long sectionId,
            Long exerciseId,
            WorkoutExerciseDto workoutExerciseDto) {

        WorkoutExercise workoutExercise = getWorkoutExercise(sectionId, exerciseId);

        if (workoutExerciseDto.exerciseId() != null &&
                !workoutExerciseDto.exerciseId()
                        .equals(workoutExercise.getExercise().getId())) {

            Exercise newExercise = exerciseRepository.findById(
                            workoutExerciseDto.exerciseId())
                    .orElseThrow(() -> GymJourneyException.notFound(
                            "Exercício não encontrado!"
                    ));

            workoutExercise.setExercise(newExercise);
        }

        workoutExercise.update(workoutExerciseDto);

        return workoutExerciseMapper.toDetailsDto(
                workoutExerciseRepository.save(workoutExercise)
        );
    }

    public Page<WorkoutExerciseDetailsDto> getBySectionId(
            Long sectionId,
            int page,
            int size) {

        getSection(sectionId);

        Pageable pageable = PageRequest.of(page, size);

        return workoutExerciseRepository
                .findAllByWorkoutSectionId(sectionId, pageable)
                .map(workoutExerciseMapper::toDetailsDto);
    }

    public WorkoutExerciseDetailsDto getById(
            Long sectionId,
            Long exerciseId) {

        return workoutExerciseMapper.toDetailsDto(
                getWorkoutExercise(sectionId, exerciseId)
        );
    }

    @Transactional
    public void delete(Long sectionId, Long exerciseId) {
        workoutExerciseRepository.delete(
                getWorkoutExercise(sectionId, exerciseId)
        );
    }

    @Transactional
    public WorkoutExerciseDetailsDto updateWeight(
            Long sectionId,
            Long exerciseId,
            Double weight,
            User authenticatedUser) {

        if (weight < 0) {
            throw GymJourneyException.badRequest("O peso não pode ser negativo");
        }

        WorkoutExercise workoutExercise = getWorkoutExercise(sectionId, exerciseId);

        if (!workoutExercise.getWorkoutSection()
                .getWorkout()
                .getUser()
                .getId()
                .equals(authenticatedUser.getId())) {

            throw GymJourneyException.forbidden(
                    "Você não pode alterar exercícios de outro usuário"
            );
        }

        workoutExercise.setWeight(weight);

        return workoutExerciseMapper.toDetailsDto(
                workoutExerciseRepository.save(workoutExercise)
        );
    }


    private WorkoutSection getSection(Long sectionId) {
        return workoutSectionRepository.findById(sectionId)
                .orElseThrow(() -> GymJourneyException.notFound(
                        "Seção de treino não encontrada!"
                ));
    }

    private WorkoutExercise getWorkoutExercise(Long sectionId, Long exerciseId) {
        WorkoutExercise workoutExercise = workoutExerciseRepository.findById(exerciseId)
                .orElseThrow(() -> GymJourneyException.notFound(
                        "Exercício do treino não encontrado!"
                ));

        if (!workoutExercise.getWorkoutSection().getId().equals(sectionId)) {
            throw GymJourneyException.badRequest(
                    "Este exercício não pertence à seção informada"
            );
        }

        return workoutExercise;
    }
}

