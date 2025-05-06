package io.github.mendjoy.gymJourneyAPI.repository;

import io.github.mendjoy.gymJourneyAPI.entity.workout.WorkoutSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkouSectionRepository extends JpaRepository<WorkoutSection, Integer> {
}
