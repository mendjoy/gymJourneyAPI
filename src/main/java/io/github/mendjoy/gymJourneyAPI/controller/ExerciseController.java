package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDto;
import io.github.mendjoy.gymJourneyAPI.dto.response.ApiResponseDto;
import io.github.mendjoy.gymJourneyAPI.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @PostMapping
    public ResponseEntity<ExerciseDetailsDto> register(@Valid @RequestBody ExerciseDto exerciseDto) {
        ExerciseDetailsDto newExercise = exerciseService.register(exerciseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newExercise);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @PutMapping("/{exerciseId}")
    public ResponseEntity<ExerciseDetailsDto> update(
            @PathVariable Long exerciseId,
            @Valid @RequestBody ExerciseDto exerciseDto) {
        ExerciseDetailsDto exercise = exerciseService.update(exerciseId, exerciseDto);
        return ResponseEntity.ok(exercise);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @GetMapping("/{exerciseId}")
    public ResponseEntity<ExerciseDetailsDto> getById(@PathVariable Long exerciseId) {
        ExerciseDetailsDto exercise = exerciseService.getById(exerciseId);
        return ResponseEntity.ok(exercise);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @GetMapping
    public ResponseEntity<Page<ExerciseDetailsDto>> getExercises(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ExerciseDetailsDto> exercises = exerciseService.getExercises(name, page, size);
        return ResponseEntity.ok(exercises);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @PostMapping("/{exerciseId}/muscle-groups/{muscleGroupId}")
    public ResponseEntity<ApiResponseDto> addMuscleGroup(
            @PathVariable Long exerciseId,
            @PathVariable Long muscleGroupId) {
        exerciseService.addMuscleGroup(exerciseId, muscleGroupId);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK.value(), "Grupo muscular adicionado"));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @DeleteMapping("/{exerciseId}/muscle-groups/{muscleGroupId}")
    public ResponseEntity<ApiResponseDto> removeMuscleGroup(
            @PathVariable Long exerciseId,
            @PathVariable Long muscleGroupId) {
        exerciseService.removeMuscleGroup(exerciseId, muscleGroupId);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK.value(), "Grupo muscular removido"));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long exerciseId) {
        exerciseService.delete(exerciseId);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK.value(), "Exercício deletado"));
    }
}