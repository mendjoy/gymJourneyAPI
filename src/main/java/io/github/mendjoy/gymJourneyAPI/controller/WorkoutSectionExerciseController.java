package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.dto.response.ApiResponseDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDto;
import io.github.mendjoy.gymJourneyAPI.service.WorkoutSectionExerciseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workout-sections/{sectionId}/exercises")
public class WorkoutSectionExerciseController {

    private final WorkoutSectionExerciseService workoutSectionExerciseService;

    public WorkoutSectionExerciseController(WorkoutSectionExerciseService workoutSectionExerciseService) {
        this.workoutSectionExerciseService = workoutSectionExerciseService;
    }

    @PostMapping
    public ResponseEntity<List<WorkoutExerciseDetailsDto>> create(
            @PathVariable Long sectionId,
            @Valid @RequestBody List<WorkoutExerciseDto> workoutExerciseDtos) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workoutSectionExerciseService.create(sectionId, workoutExerciseDtos));
    }

    @PutMapping("/{exerciseId}")
    public ResponseEntity<WorkoutExerciseDetailsDto> update(
            @PathVariable Long sectionId,
            @PathVariable Long exerciseId,
            @Valid @RequestBody WorkoutExerciseDto workoutExerciseDto) {

        return ResponseEntity.ok(
                workoutSectionExerciseService.update(sectionId, exerciseId, workoutExerciseDto)
        );
    }

    @GetMapping
    public ResponseEntity<Page<WorkoutExerciseDetailsDto>> findBySectionId(
            @PathVariable Long sectionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                workoutSectionExerciseService.getBySectionId(sectionId, page, size)
        );
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<WorkoutExerciseDetailsDto> findById(
            @PathVariable Long sectionId,
            @PathVariable Long exerciseId) {

        return ResponseEntity.ok(
                workoutSectionExerciseService.getById(sectionId, exerciseId)
        );
    }

    @PatchMapping("/{exerciseId}/weight")
    public ResponseEntity<WorkoutExerciseDetailsDto> updateWeight(
            @PathVariable Long sectionId,
            @PathVariable Long exerciseId,
            @RequestParam Double weight,
            @AuthenticationPrincipal User authenticatedUser) {

        return ResponseEntity.ok(
                workoutSectionExerciseService.updateWeight(
                        sectionId, exerciseId, weight, authenticatedUser
                )
        );
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<ApiResponseDto> delete(
            @PathVariable Long sectionId,
            @PathVariable Long exerciseId) {

        workoutSectionExerciseService.delete(sectionId, exerciseId);

        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Exercício removido do treino com sucesso!"
        ));
    }
}
