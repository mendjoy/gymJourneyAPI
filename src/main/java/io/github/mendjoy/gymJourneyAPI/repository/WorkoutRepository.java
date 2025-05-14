package io.github.mendjoy.gymJourneyAPI.repository;

import io.github.mendjoy.gymJourneyAPI.entity.user.User;
import io.github.mendjoy.gymJourneyAPI.entity.workout.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {

    List<Workout> findByUser(User user);

    @Query("SELECT w FROM Workout w WHERE w.user = :user AND (w.endDate IS NULL OR w.endDate >= CURRENT_DATE)")
    Workout findCurrentWorkout(@Param("user") User user);

}
