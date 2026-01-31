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

    @PostMapping
    public ResponseEntity<List<WorkoutSectionDto>> create(@RequestBody List<WorkoutSectionDto> workoutSectionDtos) {
        List<WorkoutSectionDto> newSections = workoutSectionService.create(workoutSectionDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSections);
    }

    @PutMapping
    public ResponseEntity<WorkoutSectionDto> update(@RequestBody WorkoutSectionDto workoutSectionDto) {
        WorkoutSectionDto section = workoutSectionService.update(workoutSectionDto);
        return ResponseEntity.ok(section);
    }

    @GetMapping
    public ResponseEntity<Page<WorkoutSectionDetailsDto>> findByWorkoutId(@RequestParam Long workoutId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<WorkoutSectionDetailsDto> sections = workoutSectionService.findByWorkoutId(workoutId, page, size);
        return ResponseEntity.ok(sections);
    }

    @GetMapping("/{sectionId}")
    public ResponseEntity<WorkoutSectionDetailsDto> findById(@PathVariable Long sectionId) {
        WorkoutSectionDetailsDto section = workoutSectionService.findById(sectionId);
        return ResponseEntity.ok(section);
    }


    @DeleteMapping("/{sectionId}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long sectionId) {
        workoutSectionService.delete(sectionId);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK.value(), "Seção do treino excluida com sucesso!"));
    }
}
