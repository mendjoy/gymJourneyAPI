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
@RequestMapping("/workout-sections")
public class WorkoutSectionController {

    private final WorkoutSectionService workoutSectionService;

    public WorkoutSectionController(WorkoutSectionService workoutSectionService) {
        this.workoutSectionService = workoutSectionService;
    }

    @PostMapping("/register")
    public ResponseEntity<List<WorkoutSectionDto>> register(@RequestBody List<WorkoutSectionDto> workoutSectionDtos) {
        List<WorkoutSectionDto> newSections = workoutSectionService.register(workoutSectionDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSections);
    }

    @GetMapping
    public ResponseEntity<Page<WorkoutSectionDetailsDto>> getByWorkoutId(@RequestParam Long workoutId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<WorkoutSectionDetailsDto> sections = workoutSectionService.getByWorkoutId(workoutId, page, size);
        return ResponseEntity.ok(sections);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutSectionDetailsDto> getById(@PathVariable Long id){
        WorkoutSectionDetailsDto section = workoutSectionService.getById(id);
        return ResponseEntity.ok(section);
    }

    @PutMapping
    public ResponseEntity<WorkoutSectionDto> update(@RequestBody WorkoutSectionDto workoutSectionDto) {
        WorkoutSectionDto section = workoutSectionService.update(workoutSectionDto);
        return ResponseEntity.ok(section);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long id){
        workoutSectionService.delete(id);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK.value(), "Seção do treino excluida com sucesso!"));
    }
}
