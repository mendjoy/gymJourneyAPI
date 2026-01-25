package io.github.mendjoy.gymJourneyAPI.controller;

import io.github.mendjoy.gymJourneyAPI.dto.muscleGroup.MuscleGroupDto;
import io.github.mendjoy.gymJourneyAPI.dto.response.ApiResponseDto;
import io.github.mendjoy.gymJourneyAPI.service.MuscleGroupService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/muscle-groups")
public class MuscleGroupController {

    private final MuscleGroupService muscleGroupService;

    public MuscleGroupController(MuscleGroupService muscleGroupService) {
        this.muscleGroupService = muscleGroupService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<List<MuscleGroupDto>> register(
            @Valid @RequestBody List<MuscleGroupDto> muscleGroupDtos) {
        List<MuscleGroupDto> newMuscleGroups = muscleGroupService.register(muscleGroupDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMuscleGroups);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<MuscleGroupDto> update(
            @PathVariable Long id,
            @Valid @RequestBody MuscleGroupDto muscleGroupDto) {
        MuscleGroupDto muscleGroup = muscleGroupService.update(id, muscleGroupDto);
        return ResponseEntity.ok(muscleGroup);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MuscleGroupDto> getById(@PathVariable Long id) {
        MuscleGroupDto muscleGroupDto = muscleGroupService.getById(id);
        return ResponseEntity.ok(muscleGroupDto);
    }

    @GetMapping
    public ResponseEntity<Page<MuscleGroupDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MuscleGroupDto> muscleGroups = muscleGroupService.getAll(page, size);
        return ResponseEntity.ok(muscleGroups);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MuscleGroupDto>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MuscleGroupDto> muscleGroups = muscleGroupService.searchByName(name, page, size);
        return ResponseEntity.ok(muscleGroups);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long id) {
        muscleGroupService.delete(id);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Grupo muscular deletado com sucesso"
        ));
    }
}
}