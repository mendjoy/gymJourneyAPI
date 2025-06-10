package io.github.mendjoy.gymJourneyAPI.repository.workout;

import io.github.mendjoy.gymJourneyAPI.entity.user.User;
import io.github.mendjoy.gymJourneyAPI.entity.workout.Workout;
import io.github.mendjoy.gymJourneyAPI.entity.workout.WorkoutFrequency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface WorkoutFrequencyRepository extends JpaRepository<WorkoutFrequency, Integer> {

    boolean existsByUserAndWorkoutAndFrequencyDate(User user, Workout workout, LocalDate date);
}
