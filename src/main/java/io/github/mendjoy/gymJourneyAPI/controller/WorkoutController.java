package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.dto.response.ApiResponseDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDto;
import io.github.mendjoy.gymJourneyAPI.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping("/register")
    public ResponseEntity<WorkoutDto> register(@Valid @RequestBody WorkoutDto workoutDto) {
        WorkoutDto newWorkout = workoutService.register(workoutDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWorkout);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDetailsDto> getById(@PathVariable Long id) {
        WorkoutDetailsDto workoutDetailsDto = workoutService.getById(id);
        return ResponseEntity.ok(workoutDetailsDto);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Page<WorkoutDetailsDto>> getByUser(@PathVariable Long id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<WorkoutDetailsDto> workoutDtos = workoutService.getByUser(id, page, size);
        return ResponseEntity.ok(workoutDtos);
    }

    @PutMapping
    public ResponseEntity<WorkoutDto> update(@Valid @RequestBody WorkoutDto workoutDto) {
        WorkoutDto newWorkout = workoutService.update(workoutDto);
        return ResponseEntity.ok(newWorkout);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long id){
        workoutService.delete(id);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK.value(), "Treino excluido com sucesso!"));
    }

}
