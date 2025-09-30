package io.github.mendjoy.gymJourneyAPI.repository;

import io.github.mendjoy.gymJourneyAPI.domain.WorkoutSection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutSectionRepository extends JpaRepository<WorkoutSection, Integer> {

    Page<WorkoutSection> findByWorkoutId(Integer workoutId, Pageable pageable);
}
