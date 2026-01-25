package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.config.mapper.MuscleGroupMapper;
import io.github.mendjoy.gymJourneyAPI.domain.MuscleGroup;
import io.github.mendjoy.gymJourneyAPI.dto.muscleGroup.MuscleGroupDto;
import io.github.mendjoy.gymJourneyAPI.repository.ExerciseRepository;
import io.github.mendjoy.gymJourneyAPI.repository.MuscleGroupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MuscleGroupService {

    private final MuscleGroupRepository muscleGroupRepository;
    private final MuscleGroupMapper muscleGroupMapper;
    private final ExerciseRepository exerciseRepository;

    public MuscleGroupService(MuscleGroupRepository muscleGroupRepository,
                              MuscleGroupMapper muscleGroupMapper, ExerciseRepository exerciseRepository) {
        this.muscleGroupRepository = muscleGroupRepository;
        this.muscleGroupMapper = muscleGroupMapper;
        this.exerciseRepository = exerciseRepository;
    }

    @Transactional
    public List<MuscleGroupDto> register(List<MuscleGroupDto> muscleGroupDtos) {
        List<String> duplicates = new ArrayList<>();
        List<MuscleGroup> toSave = new ArrayList<>();

        for (MuscleGroupDto dto : muscleGroupDtos) {
            if (muscleGroupRepository.existsByName(dto.name())) {
                duplicates.add(dto.name());
            } else {
                toSave.add(muscleGroupMapper.toEntity(dto));
            }
        }

        if (!duplicates.isEmpty()) {
            String duplicateNames = String.join(", ", duplicates);
            throw GymJourneyException.alreadyExists(
                    "Os seguintes grupos musculares já existem: " + duplicateNames
            );
        }

        if (toSave.isEmpty()) {
            throw GymJourneyException.badRequest(
                    "Nenhum grupo muscular válido para cadastrar"
            );
        }

        List<MuscleGroup> savedMuscleGroups = muscleGroupRepository.saveAll(toSave);

        return savedMuscleGroups.stream()
                .map(muscleGroupMapper::toDto)
                .toList();
    }

    @Transactional
    public MuscleGroupDto update(Long id, MuscleGroupDto muscleGroupDto) {
        MuscleGroup muscleGroup = muscleGroupRepository.findById(id)
                .orElseThrow(() -> GymJourneyException.notFound(
                        "Grupo muscular não encontrado: " + id
                ));

        if (muscleGroupDto.name() != null &&
                !muscleGroupDto.name().equals(muscleGroup.getName()) &&
                muscleGroupRepository.existsByName(muscleGroupDto.name())) {
            throw GymJourneyException.alreadyExists(
                    "Grupo muscular '" + muscleGroupDto.name() + "' já existe"
            );
        }

        if (muscleGroupDto.name() == null || muscleGroupDto.name().isBlank()) {
            throw GymJourneyException.badRequest("O nome do grupo muscular é obrigatório");
        }

        muscleGroup.setName(muscleGroupDto.name());

        MuscleGroup updatedMuscleGroup = muscleGroupRepository.save(muscleGroup);
        return muscleGroupMapper.toDto(updatedMuscleGroup);
    }

    public MuscleGroupDto getById(Long id) {
        MuscleGroup muscleGroup = muscleGroupRepository.findById(id)
                .orElseThrow(() -> GymJourneyException.notFound(
                        "Grupo muscular não encontrado: " + id
                ));
        return muscleGroupMapper.toDto(muscleGroup);
    }

    public Page<MuscleGroupDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MuscleGroup> muscleGroupPage = muscleGroupRepository.findAll(pageable);
        return muscleGroupPage.map(muscleGroupMapper::toDto);
    }

    public Page<MuscleGroupDto> searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MuscleGroup> muscleGroupPage = muscleGroupRepository
                .findByNameContainingIgnoreCase(name, pageable);
        return muscleGroupPage.map(muscleGroupMapper::toDto);
    }

    @Transactional
    public void delete(Long id) {
        MuscleGroup muscleGroup = muscleGroupRepository.findById(id)
                .orElseThrow(() -> GymJourneyException.notFound(
                        "Grupo muscular não encontrado: " + id
                ));

        if (exerciseRepository.existsByMuscleGroupsContaining(muscleGroup)) {
            throw GymJourneyException.conflict(
                 "Não é possível deletar este grupo muscular pois ele está sendo usado em exercícios"
              );
         }

        muscleGroupRepository.delete(muscleGroup);
    }
}