package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.config.mapper.MuscleGroupMapper;
import io.github.mendjoy.gymJourneyAPI.domain.MuscleGroup;
import io.github.mendjoy.gymJourneyAPI.dto.muscleGroup.MuscleGroupDto;
import io.github.mendjoy.gymJourneyAPI.repository.MuscleGroupRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuscleGroupService {

    private final MuscleGroupRepository muscleGroupRepository;
    private final MuscleGroupMapper muscleGroupMapper;

    public MuscleGroupService(MuscleGroupRepository muscleGroupRepository, MuscleGroupMapper muscleGroupMapper) {
        this.muscleGroupRepository = muscleGroupRepository;
        this.muscleGroupMapper = muscleGroupMapper;
    }

    public List<MuscleGroupDto> register(@Valid List<MuscleGroupDto> muscleGroupDtos) {
        List<MuscleGroup> toSave = muscleGroupDtos.stream()
                .filter(dto -> !muscleGroupRepository.existsByName(dto.name()))
                .map(muscleGroupMapper::toEntity)
                .toList();

        List<MuscleGroup> newMuscleGroups = muscleGroupRepository.saveAll(toSave);

        return newMuscleGroups.stream()
                .map(muscleGroupMapper::toDto)
                .toList();
    }

    public MuscleGroupDto update(MuscleGroupDto muscleGroupDto) {
        MuscleGroup muscleGroup = muscleGroupRepository.findById(muscleGroupDto.id()).orElseThrow();
        muscleGroup.setName(muscleGroupDto.name());

        MuscleGroup updatedMuscleGroup = muscleGroupRepository.save(muscleGroup);
        return muscleGroupMapper.toDto(updatedMuscleGroup);
    }

    public MuscleGroupDto getById(Long id) {
        MuscleGroup muscleGroup = muscleGroupRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Grupo muscular não encontrado: " + id));
        return muscleGroupMapper.toDto(muscleGroup);
    }

    public Page<MuscleGroupDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MuscleGroup> muscleGroupPage = muscleGroupRepository.findAll(pageable);
        return muscleGroupPage.map(muscleGroupMapper::toDto);
    }

    public Page<MuscleGroupDto> searchByName(String name, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<MuscleGroup> muscleGroupPage = muscleGroupRepository.findByNameContainingIgnoreCase(name, pageable);
        return muscleGroupPage.map(muscleGroupMapper::toDto);
    }

}
