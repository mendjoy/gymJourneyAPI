package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.dto.WorkoutDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.WorkoutDto;
import io.github.mendjoy.gymJourneyAPI.service.WorkoutService;
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
    public ResponseEntity<WorkoutDto> register(@RequestBody WorkoutDto workoutDto) {
        WorkoutDto newWorkoutDetailsDTO = workoutService.register(workoutDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWorkoutDetailsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDetailsDto> getWorkoutById(@PathVariable Integer id) {
        WorkoutDetailsDto workoutDetailsDto = workoutService.getWorkoutById(id);
        return ResponseEntity.ok(workoutDetailsDto);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Page<WorkoutDetailsDto>> getWorkoutsByUser(@PathVariable Integer id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<WorkoutDetailsDto> workoutDtos = workoutService.getWorkoutsByUser(id, page, size);
        return ResponseEntity.ok(workoutDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkout (@PathVariable Integer id){
        workoutService.deleteWorkout(id);
        return ResponseEntity.ok("Treino deletado com sucesso!");
    }

}
