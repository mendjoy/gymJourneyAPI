package io.github.mendjoy.gymJourneyAPI.repository;

import io.github.mendjoy.gymJourneyAPI.entity.user.User;
import io.github.mendjoy.gymJourneyAPI.entity.workout.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {

    List<Workout> findByUser(User user);

    List<Workout> findByUserIdAndEndDateIsNull(Integer userId);

}
