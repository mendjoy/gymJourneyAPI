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
    public ResponseEntity<List<WorkoutExerciseDto>> register(@RequestBody List<WorkoutExerciseDto> workoutExerciseDto) {
        List<WorkoutExerciseDto> newWorkoutSections = workoutExerciseService.register(workoutExerciseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWorkoutSections);
    }

    @PutMapping
    public ResponseEntity<WorkoutExerciseDto> update(@RequestBody WorkoutExerciseDto workoutExerciseDto) {
        WorkoutExerciseDto workoutExercise = workoutExerciseService.update(workoutExerciseDto);
        return ResponseEntity.ok(workoutExercise);
    }

    @GetMapping
    public ResponseEntity<Page<WorkoutExerciseDetailsDto>> getBySectionId(@RequestParam Long sectionId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<WorkoutExerciseDetailsDto> exercises = workoutExerciseService.getBySectionId(sectionId, page, size);
        return ResponseEntity.ok(exercises);
    }



    //@PutMapping
   // public ResponseEntity<ApiResponseDto> updateWeight(@RequestParam Double Weight, @AuthenticationPrincipal User authenticatedUser){

   // }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long id){
        workoutExerciseService.delete(id);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK.value(), "Exercicio removido do treino com sucesso!"));
    }
}
