package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.dto.WorkoutExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.WorkoutExerciseDto;
import io.github.mendjoy.gymJourneyAPI.service.WorkoutExerciseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workout-exercises")
public class WorkoutExerciseController {

    private final WorkoutExerciseService workoutExerciseService;

    public WorkoutExerciseController(WorkoutExerciseService workoutExerciseService) {
        this.workoutExerciseService = workoutExerciseService;
    }

    @PostMapping("/register")
    public ResponseEntity<List<WorkoutExerciseDto>> register(@RequestBody List<WorkoutExerciseDto> workoutExerciseDto) {
        List<WorkoutExerciseDto> newWorkoutSections = workoutExerciseService.register(workoutExerciseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWorkoutSections);
    }

    @GetMapping
    public ResponseEntity<Page<WorkoutExerciseDetailsDto>> getExercisesBySectionId(@RequestParam Integer sectionId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<WorkoutExerciseDetailsDto> exercises = workoutExerciseService.getExercisesBySectionId(sectionId, page, size);
        return ResponseEntity.ok(exercises);
    }
}
