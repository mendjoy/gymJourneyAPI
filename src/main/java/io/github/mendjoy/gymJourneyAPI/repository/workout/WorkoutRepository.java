package io.github.mendjoy.gymJourneyAPI.repository.workout;

import io.github.mendjoy.gymJourneyAPI.entity.user.User;
import io.github.mendjoy.gymJourneyAPI.entity.workout.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {

    List<Workout> findByUser(User user);

    @Query("SELECT w FROM Workout w WHERE w.user = :user AND w.startDate <= CURRENT_DATE AND (w.endDate IS NULL OR w.endDate >= CURRENT_DATE)")
    Optional<Workout> findCurrentWorkout(@Param("user") User user);

    @Query("SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END " +
    "FROM Workout w WHERE w.user = :user AND (w.endDate IS NULL OR w.endDate >= :newStartDate)")
    boolean existsWorkoutInProgressForUser(@Param("user") User user, @Param("newStartDate") LocalDate newStartDate);

}
