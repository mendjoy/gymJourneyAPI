package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.dto.response.ApiResponseDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDto;
import io.github.mendjoy.gymJourneyAPI.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    public ResponseEntity<WorkoutDetailsDto> create(@Valid @RequestBody WorkoutDto workoutDto) {
        WorkoutDetailsDto newWorkout = workoutService.create(workoutDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWorkout);
    }

    @PutMapping("/{workoutId}")
    public ResponseEntity<WorkoutDetailsDto> update(
            @PathVariable Long workoutId,
            @Valid @RequestBody WorkoutDto workoutDto) {
        WorkoutDetailsDto updatedWorkout = workoutService.update(workoutId, workoutDto);
        return ResponseEntity.ok(updatedWorkout);
    }

    @GetMapping("/{workoutId}")
    public ResponseEntity<WorkoutDetailsDto> findById(
            @PathVariable Long workoutId,
            @AuthenticationPrincipal User authenticatedUser) {
        WorkoutDetailsDto workoutDetailsDto = workoutService.findById(workoutId, authenticatedUser);
        return ResponseEntity.ok(workoutDetailsDto);
    }

    @GetMapping
    public ResponseEntity<Page<WorkoutDetailsDto>> findWorkouts(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false, defaultValue = "false") Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal User authenticatedUser) {

        Page<WorkoutDetailsDto> workouts =
                workoutService.findWorkouts(userId, active, page, size, authenticatedUser);

        return ResponseEntity.ok(workouts);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long workoutId) {
        workoutService.delete(workoutId);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Treino excluído com sucesso!"
        ));
    }
}