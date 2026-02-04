package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.dto.response.ApiResponseDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDto;
import io.github.mendjoy.gymJourneyAPI.service.WorkoutSectionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts/{workoutId}/sections")
public class WorkoutSectionController {

    private final WorkoutSectionService workoutSectionService;

    public WorkoutSectionController(WorkoutSectionService workoutSectionService) {
        this.workoutSectionService = workoutSectionService;
    }

    @PostMapping
    public ResponseEntity<List<WorkoutSectionDto>> create(
            @PathVariable Long workoutId,
            @RequestBody List<WorkoutSectionDto> workoutSectionDtos) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workoutSectionService.create(workoutId, workoutSectionDtos));
    }

    @PutMapping("/{sectionId}")
    public ResponseEntity<WorkoutSectionDto> update(
            @PathVariable Long workoutId,
            @PathVariable Long sectionId,
            @RequestBody WorkoutSectionDto workoutSectionDto) {

        return ResponseEntity.ok(
                workoutSectionService.update(workoutId, sectionId, workoutSectionDto)
        );
    }

    @GetMapping
    public ResponseEntity<Page<WorkoutSectionDetailsDto>> findAll(
            @PathVariable Long workoutId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                workoutSectionService.findByWorkoutId(workoutId, page, size)
        );
    }

    @GetMapping("/{sectionId}")
    public ResponseEntity<WorkoutSectionDetailsDto> findById(
            @PathVariable Long workoutId,
            @PathVariable Long sectionId) {

        return ResponseEntity.ok(
                workoutSectionService.findById(workoutId, sectionId)
        );
    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<ApiResponseDto> delete(
            @PathVariable Long workoutId,
            @PathVariable Long sectionId) {

        workoutSectionService.delete(workoutId, sectionId);

        return ResponseEntity.ok(
                new ApiResponseDto(
                        HttpStatus.OK.value(),
                        "Seção do treino excluída com sucesso!"
                )
        );
    }
}

