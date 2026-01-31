package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.dto.response.ApiResponseDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDto;
import io.github.mendjoy.gymJourneyAPI.service.WorkoutExerciseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workout-sections/{sectionId}/exercises")
public class WorkoutExerciseController {

    private final WorkoutExerciseService workoutExerciseService;

    public WorkoutExerciseController(WorkoutExerciseService workoutExerciseService) {
        this.workoutExerciseService = workoutExerciseService;
    }

    @PostMapping
    public ResponseEntity<List<WorkoutExerciseDetailsDto>> create(
            @PathVariable Long sectionId,
            @Valid @RequestBody List<WorkoutExerciseDto> workoutExerciseDtos) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workoutExerciseService.create(sectionId, workoutExerciseDtos));
    }

    @PutMapping("/{exerciseId}")
    public ResponseEntity<WorkoutExerciseDetailsDto> update(
            @PathVariable Long sectionId,
            @PathVariable Long exerciseId,
            @Valid @RequestBody WorkoutExerciseDto workoutExerciseDto) {

        return ResponseEntity.ok(
                workoutExerciseService.update(sectionId, exerciseId, workoutExerciseDto)
        );
    }

    @GetMapping
    public ResponseEntity<Page<WorkoutExerciseDetailsDto>> findBySectionId(
            @PathVariable Long sectionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                workoutExerciseService.getBySectionId(sectionId, page, size)
        );
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<WorkoutExerciseDetailsDto> findById(
            @PathVariable Long sectionId,
            @PathVariable Long exerciseId) {

        return ResponseEntity.ok(
                workoutExerciseService.getById(sectionId, exerciseId)
        );
    }

    @PatchMapping("/{exerciseId}/weight")
    public ResponseEntity<WorkoutExerciseDetailsDto> updateWeight(
            @PathVariable Long sectionId,
            @PathVariable Long exerciseId,
            @RequestParam Double weight,
            @AuthenticationPrincipal User authenticatedUser) {

        return ResponseEntity.ok(
                workoutExerciseService.updateWeight(
                        sectionId, exerciseId, weight, authenticatedUser
                )
        );
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<ApiResponseDto> delete(
            @PathVariable Long sectionId,
            @PathVariable Long exerciseId) {

        workoutExerciseService.delete(sectionId, exerciseId);

        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Exercício removido do treino com sucesso!"
        ));
    }
}
