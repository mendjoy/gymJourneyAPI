package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDto;
import io.github.mendjoy.gymJourneyAPI.dto.muscleGroup.MuscleGroupDto;
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
    @PutMapping("/{id}")
    public ResponseEntity<ExerciseDetailsDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ExerciseDto exerciseDto) {
        ExerciseDetailsDto exercise = exerciseService.update(id, exerciseDto);
        return ResponseEntity.ok(exercise);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDetailsDto> getById(@PathVariable Long id) {
        ExerciseDetailsDto exercise = exerciseService.getById(id);
        return ResponseEntity.ok(exercise);
    }

    @GetMapping
    public ResponseEntity<Page<ExerciseDetailsDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ExerciseDetailsDto> exercises = exerciseService.getAll(page, size);
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ExerciseDetailsDto>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ExerciseDetailsDto> exercises = exerciseService.searchByName(name, page, size);
        return ResponseEntity.ok(exercises);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @PatchMapping("/{id}/muscle-groups/add")
    public ResponseEntity<ApiResponseDto> addMuscleGroup(
            @PathVariable Long id,
            @Valid @RequestBody MuscleGroupDto muscleGroupDto) {
        exerciseService.addMuscleGroup(id, muscleGroupDto);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Grupo muscular adicionado com sucesso"
        ));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @PatchMapping("/{id}/muscle-groups/remove")
    public ResponseEntity<ApiResponseDto> removeMuscleGroup(
            @PathVariable Long id,
            @Valid @RequestBody MuscleGroupDto muscleGroupDto) {
        exerciseService.removeMuscleGroup(id, muscleGroupDto);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Grupo muscular removido com sucesso"
        ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long id) {
        exerciseService.delete(id);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Exercício deletado com sucesso"
        ));
    }
}