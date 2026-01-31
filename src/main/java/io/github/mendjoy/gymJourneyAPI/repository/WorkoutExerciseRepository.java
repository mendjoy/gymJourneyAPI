package io.github.mendjoy.gymJourneyAPI.repository;

import io.github.mendjoy.gymJourneyAPI.domain.WorkoutSectionExercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutSectionExercise, Long> {

    Page<WorkoutSectionExercise> findAllByWorkoutSectionId(Long workoutSectionId, Pageable pageable);

    boolean existsByExerciseId(Long exerciseId);
}
