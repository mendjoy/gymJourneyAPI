package io.github.mendjoy.gymJourneyAPI.repository;

import io.github.mendjoy.gymJourneyAPI.domain.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Long> {
}
