package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.dto.WorkoutSectionDetailsDto;
import io.github.mendjoy.gymJourneyAPI.dto.WorkoutSectionDto;
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
    public ResponseEntity<Page<WorkoutSectionDetailsDto>> getSectionsByWorkoutId(@RequestParam Integer workoutId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<WorkoutSectionDetailsDto> sections = workoutSectionService.getSectionsByWorkoutId(workoutId, page, size);
        return ResponseEntity.ok(sections);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutSectionDetailsDto> getWorkoutSectionById(@PathVariable Integer id){
        WorkoutSectionDetailsDto section = workoutSectionService.getWorkoutSectionById(id);
        return ResponseEntity.ok(section);
    }

    @PatchMapping
    public ResponseEntity<WorkoutSectionDto> updateWorkoutSection(@RequestBody WorkoutSectionDto workoutSectionDto) {
        WorkoutSectionDto section = workoutSectionService.updateWorkoutSection(workoutSectionDto);
        return ResponseEntity.ok(section);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkoutSection (@PathVariable Integer id){
        workoutSectionService.deleteWorkoutSection(id);
        return ResponseEntity.ok("Seção deletada com sucesso!");
    }

}
