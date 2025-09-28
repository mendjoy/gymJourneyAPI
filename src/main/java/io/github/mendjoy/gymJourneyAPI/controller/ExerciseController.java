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

    @PostMapping("/register")
    public ResponseEntity<ExerciseDto> register(@RequestBody ExerciseDto exerciseDto){
        ExerciseDto newExercise = exerciseService.register(exerciseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newExercise);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDto> getExerciseById(@RequestParam Integer id){
        ExerciseDto exercise = exerciseService.getExerciseById(id);
        return ResponseEntity.ok(exercise);
    }

    @GetMapping
    public ResponseEntity<Page<ExerciseDto>> getAllExercises(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<ExerciseDto> exerciseDTOS = exerciseService.getAllExercises(page, size);
        return ResponseEntity.ok(exerciseDTOS);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ExerciseDto>> searchExercisesByName(@RequestParam String name,@RequestParam(defaultValue = "0") int page,  @RequestParam(defaultValue = "10") int size ){
        Page<ExerciseDto> exerciseDTOS = exerciseService.searchExercisesByName(name, page, size);
        return ResponseEntity.ok(exerciseDTOS);
    }
}
