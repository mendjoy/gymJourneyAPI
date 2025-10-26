package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.dto.response.ApiResponseDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDto;
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
    public ResponseEntity<List<WorkoutExerciseDto>> registerWorkoutExercise(@RequestBody List<WorkoutExerciseDto> workoutExerciseDto) {
        List<WorkoutExerciseDto> newWorkoutSections = workoutExerciseService.registerWorkoutExercise(workoutExerciseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWorkoutSections);
    }

    @GetMapping
    public ResponseEntity<Page<WorkoutExerciseDetailsDto>> getExercisesBySectionId(@RequestParam Long sectionId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<WorkoutExerciseDetailsDto> exercises = workoutExerciseService.getExercisesBySectionId(sectionId, page, size);
        return ResponseEntity.ok(exercises);
    }

    @PutMapping
    public ResponseEntity<WorkoutExerciseDto> updateWorkoutExercise(@RequestBody WorkoutExerciseDto workoutExerciseDto) {
        WorkoutExerciseDto workoutExercise = workoutExerciseService.updateWorkoutExercise(workoutExerciseDto);
        return ResponseEntity.ok(workoutExercise);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteWorkoutExercise (@PathVariable Long id){
        workoutExerciseService.deleteWorkoutExercise(id);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK.value(), "Exercicio removido do treino com sucesso!"));
    }
}
