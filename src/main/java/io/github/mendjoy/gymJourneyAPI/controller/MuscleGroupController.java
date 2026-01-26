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
    @PostMapping
    public ResponseEntity<List<MuscleGroupDto>> create(
            @Valid @RequestBody List<MuscleGroupDto> muscleGroupDtos) {
        List<MuscleGroupDto> newMuscleGroups = muscleGroupService.create(muscleGroupDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMuscleGroups);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{muscleGroupId}")
    public ResponseEntity<MuscleGroupDto> update(
            @PathVariable Long muscleGroupId,
            @Valid @RequestBody MuscleGroupDto muscleGroupDto) {
        MuscleGroupDto muscleGroup = muscleGroupService.update(muscleGroupId, muscleGroupDto);
        return ResponseEntity.ok(muscleGroup);
    }

    @GetMapping("/{muscleGroupId}")
    public ResponseEntity<MuscleGroupDto> getById(@PathVariable Long muscleGroupId) {
        MuscleGroupDto muscleGroupDto = muscleGroupService.getById(muscleGroupId);
        return ResponseEntity.ok(muscleGroupDto);
    }

    @GetMapping
    public ResponseEntity<Page<MuscleGroupDto>> findMuscleGroups(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<MuscleGroupDto> muscleGroups = muscleGroupService.findMuscleGroups(name, page, size);
        return ResponseEntity.ok(muscleGroups);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{muscleGroupId}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long muscleGroupId) {
        muscleGroupService.delete(muscleGroupId);
        return ResponseEntity.ok(new ApiResponseDto(
                HttpStatus.OK.value(),
                "Grupo muscular deletado com sucesso"
        ));
    }
}