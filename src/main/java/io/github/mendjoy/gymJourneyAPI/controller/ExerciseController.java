package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.dto.ExerciseDto;
import io.github.mendjoy.gymJourneyAPI.service.ExerciseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping("/registerWorkout")
    public ResponseEntity<ExerciseDto> register(@RequestBody ExerciseDto exerciseDto){
        ExerciseDto newExercise = exerciseService.registerExercise(exerciseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newExercise);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDto> getExerciseById(@RequestParam Integer id){
        ExerciseDto exercise = exerciseService.getExerciseById(id);
        return ResponseEntity.ok(exercise);
    }

    @GetMapping
    public ResponseEntity<Page<ExerciseDto>> getAllExercises(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<ExerciseDto> exercises = exerciseService.getAllExercises(page, size);
        return ResponseEntity.ok(exercises);
    }

    @PatchMapping
    public ResponseEntity<ExerciseDto> update(@RequestBody ExerciseDto exerciseDto) {
        ExerciseDto exercise = exerciseService.updateExercise(exerciseDto);
        return ResponseEntity.ok(exercise);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ExerciseDto>> searchExercisesByName(@RequestParam String name,@RequestParam(defaultValue = "0") int page,  @RequestParam(defaultValue = "10") int size ){
        Page<ExerciseDto> exercises = exerciseService.searchExercisesByName(name, page, size);
        return ResponseEntity.ok(exercises);
    }
}
