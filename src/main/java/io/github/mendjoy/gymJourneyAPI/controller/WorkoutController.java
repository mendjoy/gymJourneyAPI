package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.dto.WorkoutDto;
import io.github.mendjoy.gymJourneyAPI.service.WorkoutService;
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
    public ResponseEntity<WorkoutDto> register(@RequestBody WorkoutDto WorkoutDto) {
        WorkoutDto newWorkoutDTO = workoutService.register(WorkoutDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWorkoutDTO);
    }

    @GetMapping
    public ResponseEntity<WorkoutDto> getWorkoutById(@RequestParam Integer id) {
        WorkoutDto workoutDto = workoutService.getWorkoutById(id);
        return ResponseEntity.ok(workoutDto);
    }

}
