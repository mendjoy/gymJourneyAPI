package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.dto.muscleGroup.MuscleGroupDto;
import io.github.mendjoy.gymJourneyAPI.service.MuscleGroupService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/muscle-group")
public class MuscleGroupController {

    private final MuscleGroupService muscleGroupService;

    public MuscleGroupController(MuscleGroupService muscleGroupService) {
        this.muscleGroupService = muscleGroupService;
    }

    @PostMapping
    public ResponseEntity<List<MuscleGroupDto>> register(@Valid @RequestBody List<MuscleGroupDto> muscleGroupDtos){
        List<MuscleGroupDto> newMuscleGroup = muscleGroupService.register(muscleGroupDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMuscleGroup);
    }

    @PutMapping
    public ResponseEntity<MuscleGroupDto> update(@Valid @RequestBody MuscleGroupDto muscleGroupDto) {
        MuscleGroupDto muscleGroup = muscleGroupService.update(muscleGroupDto);
        return ResponseEntity.ok(muscleGroup);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MuscleGroupDto> getById(@RequestParam Long id){
        MuscleGroupDto muscleGroupDto = muscleGroupService.getById(id);
        return ResponseEntity.ok(muscleGroupDto);
    }

    @GetMapping
    public ResponseEntity<Page<MuscleGroupDto>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<MuscleGroupDto> muscleGroupDto = muscleGroupService.getAll(page, size);
        return ResponseEntity.ok(muscleGroupDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MuscleGroupDto>> searchByName(@RequestParam String name, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size ){
        Page<MuscleGroupDto> muscleGroupDto = muscleGroupService.searchByName(name, page, size);
        return ResponseEntity.ok(muscleGroupDto);
    }
}
